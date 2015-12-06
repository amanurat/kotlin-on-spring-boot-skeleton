package com.example.kotlin.springboot.security.domain.services

import com.querydsl.jpa.impl.JPAQuery
import com.example.kotlin.springboot.domain.services.BaseService
import com.example.kotlin.springboot.error.AppError
import com.example.kotlin.springboot.error.Errors
import com.example.kotlin.springboot.security.RoleType
import com.example.kotlin.springboot.security.domain.models.AccessToken
import com.example.kotlin.springboot.security.domain.models.QUser
import com.example.kotlin.springboot.security.domain.models.User
import com.example.kotlin.springboot.security.domain.repositories.UserRepository
import org.apache.commons.lang.RandomStringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.sql.Timestamp
import java.util.*
import javax.persistence.EntityManager

@Service @Transactional(rollbackFor = arrayOf(Exception::class))
open class UserService : BaseService {

    @Autowired
    open protected lateinit var entityManager: EntityManager
    private val query: JPAQuery<User>
        get() = JPAQuery(entityManager)

    @Autowired
    open protected lateinit var userRepo: UserRepository

    @Autowired
    open protected lateinit var roleService: RoleService

    @Autowired
    open protected lateinit var tokenService: AccessTokenService

    open fun create(type: RoleType, loginID: String, password: String) : User {
        val uid = generateUID() as String
        val role = roleService.findOrCreateByType(type)
        val user = userRepo.save(User(type.name.toLowerCase(), uid, loginID, password, role))
        return resetToken(user)
    }

    fun safetyUpdate(user: User, updatedUser: (User) -> Unit ) : User {
        user.resetActivationData()
        updatedUser(user)
        user.configureActivationData()
        return userRepo.save(user)
    }

    fun softDestroy(user: User) {
        user.deletedAt = Timestamp(System.currentTimeMillis())
        userRepo.save(user)
    }

    fun hardDestroy(user: User) {
        userRepo.delete(user)
    }

    fun activate(user: User, code: String) : User {
        return if (user.activate(code)){
            try {
                userRepo.save(user)
            } catch(e: Exception) {
                throw AppError(Errors.UserFailedToSave, e, "activate")
            }
        } else {
            throw AppError(Errors.UserFailedToActivation, null)
        }
    }

    fun authenticate(type: RoleType, loginID: String, password: String) : User? {
        return findByLoginID(type, loginID)?.
                let {
                    if(it.authenticate(password)) it
                    else null
                } ?: throw AppError(Errors.UserNotFound, null, "login_id=($loginID)")
    }

    fun resetToken(user: User) : User {
        val accessToken = user.accessToken ?: AccessToken(user)
        user.accessToken = tokenService.refreshToken(accessToken)
        return user
    }

    fun resetToken(token: UUID) : User {
        tokenService.findByRefreshToken(token)?.let {
            return resetToken(user = it.user)
        }
        throw AppError(Errors.RefreshTokenNotFound, null)
    }

    fun findByUID(uid: String, throwable: Boolean = false) : User? {
        val user = userRepo.findByUid(uid)
        return if(throwable)
            user?.let { it } ?: throw AppError(Errors.UserNotFound, null, "uid=($uid)")
        else
            user
    }

    fun findByLoginID(type: RoleType, loginID: String, throwable: Boolean = false) : User? {
        val u = QUser.user
        val user = query.from(u).where(u._type.eq(type.name.toLowerCase()),
                u.loginId.eq(loginID), u.deletedAt.isNull).fetchOne()

        return if(throwable)
            user?.let { it } ?: throw AppError(Errors.UserNotFound, null, "login_id=($loginID)")
        else
            user
    }

    private fun generateUID() : String? {
        val str = RandomStringUtils.randomAlphanumeric(User.UIDLength).toLowerCase()
        if(null == findByUID(str, false))
            return str
        else
            return generateUID()
    }
}