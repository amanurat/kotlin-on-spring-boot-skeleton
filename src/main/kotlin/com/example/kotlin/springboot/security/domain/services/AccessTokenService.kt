package com.example.kotlin.springboot.security.domain.services

import com.example.kotlin.springboot.AWS
import com.example.kotlin.springboot.error.AppError
import com.example.kotlin.springboot.error.Errors
import com.example.kotlin.springboot.security.domain.models.AccessToken
import com.example.kotlin.springboot.security.domain.repositories.AccessTokenRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.sql.Timestamp
import java.util.*

@Service @Transactional(rollbackFor = arrayOf(Exception::class))
open class AccessTokenService {

    @Autowired
    open lateinit var Cognito: AWS.CognitoManager

    @Autowired
    open lateinit var tokenRepo: AccessTokenRepository

    fun invalidate(accessToken: AccessToken) {
        accessToken.expiresIn = Timestamp(System.currentTimeMillis() - 1)
        tokenRepo.save(accessToken)
    }

    fun refreshToken(accessToken: AccessToken) : AccessToken {
        val result = Cognito.getIdentityWithToken(accessToken.user)
        accessToken.reset()

        val accessToken = tokenRepo.save(accessToken)
        accessToken.identityId = result.identityId
        accessToken.cognitToken = result.token
        return accessToken
    }

    fun findByValidTokenAtDate(token: UUID, timestamp: Timestamp, throwable: Boolean = false): AccessToken? {
        val token = tokenRepo.findByValidTokenAtDate(token, timestamp)
        return if(throwable) {
            token?.let { it } ?: throw AppError(Errors.RefreshTokenNotFound, null)
        } else {
            token
        }
    }

    fun findByRefreshToken(token: UUID, throwable: Boolean = false): AccessToken? {
        val token = tokenRepo.findByRefreshToken(token)
        return if(throwable) {
            token?.let { it } ?: throw AppError(Errors.RefreshTokenNotFound, null)
        } else {
            token
        }
    }
}