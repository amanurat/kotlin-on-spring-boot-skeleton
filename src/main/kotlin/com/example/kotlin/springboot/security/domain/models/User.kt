package com.example.kotlin.springboot.security.domain.models

import com.fasterxml.jackson.annotation.JsonIgnore
import com.querydsl.jpa.impl.JPAQuery
import com.example.kotlin.springboot.domain.models.BaseModel
import org.apache.commons.lang.RandomStringUtils
import org.hibernate.validator.constraints.Email
import org.hibernate.validator.constraints.NotEmpty
import org.mindrot.jbcrypt.BCrypt
import java.sql.Timestamp
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "users")
class User : BaseModel {
    companion object {
        val UIDLength = 16
        private val ActivationExpires = 30*60*1000
        private val ActivationTempDataSeparator = "&&|&&"
    }

    enum class ColumnType {
        LoginID, Password;
    }

    @Column(nullable = false)
    private lateinit var _type: String

    @Column(nullable = false, unique = true, length = 16)
    var uid: String = ""
        private set

    @NotEmpty @Email
    @Column(name = "login_id", nullable = false, length = 255)
    lateinit var loginId: String

    @Column(name = "password", nullable = false)
    private lateinit var _password: String
    var password: String
        set(value) { _password = BCrypt.hashpw(value, BCrypt.gensalt()) }
        get() = _password


    @Column(columnDefinition = "TEXT")
    var activationTempData: String? = null
        private set
    var activationCode: String? = null
        private set
    var activationExpiresIn: Timestamp? = null
        private set

    @Column(name = "deletedAt")
    var deletedAt: Timestamp? = null

    @Column(nullable = false, updatable = false)
    lateinit var createdAt: Timestamp

    @Column(nullable = false)
    lateinit var updatedAt: Timestamp

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = arrayOf(CascadeType.ALL))
    @JoinTable(name = "user_role",
            joinColumns = arrayOf(JoinColumn(name = "user_id")),
            inverseJoinColumns = arrayOf(JoinColumn(name = "role_id")))
    var roles: MutableSet<Role> = HashSet()

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = arrayOf(CascadeType.ALL))
    var accessToken: AccessToken? = null

    constructor()
    constructor(type: String, uid: String, loginId: String, password: String, role: Role) {
        this._type = type
        this.roles.add(role)
        this.uid = uid
        this.loginId = loginId
        this.password = password
    }

    /*
     * Callbacks
     */

    @PrePersist
    override fun beforeCreate() {
        if(0 > id)
            IllegalArgumentException("${this.javaClass}#id is not set.")

        val now = Timestamp(System.currentTimeMillis())
        createdAt = now
        updatedAt = now
    }

    @PreUpdate
    override fun beforeUpdate() {
        updatedAt = Timestamp(System.currentTimeMillis())
    }

    /*
     * Authorizable
     */

    fun authenticate(password: String): Boolean {
        return BCrypt.checkpw(password, this.password)
    }

    fun isAuthenticated(token: UUID): Boolean {
        return accessToken?.let {
            val now = Timestamp(System.currentTimeMillis())
            if(it.expiresIn.before(now)) {
                false
            } else {
                it.token.equals(token)
            }
        } ?: false
    }

    /*
     * Activation
     */

    fun activate(code: String) : Boolean {
        if(activationTempData.isNullOrBlank().not() && activationCode.equals(code)) {
            activationExpiresIn?.let { it ->
                if (Timestamp(System.currentTimeMillis()).before(it)) {
                    val items = getActivateTempData2hashMap()
                    for(key in items.keys) {
                        when(ColumnType.valueOf(key)){
                            ColumnType.LoginID -> items.get(key)?.let { loginId = it }
                            ColumnType.Password -> items.get(key)?.let { password = it }
                        }
                    }
                    resetActivationData()
                    return true
                }
            }
        }
        return false
    }

    fun resetActivationData() {
        activationTempData = null
        activationExpiresIn = null
        activationCode = null
    }

    fun configureActivationData() : User {
        activationTempData?.let {
            activationExpiresIn = Timestamp(System.currentTimeMillis() + ActivationExpires)
            activationCode = RandomStringUtils.randomNumeric(4)
        }
        return this
    }

    fun addActivateTempData(type: ColumnType, value: String) {
        val items = getActivateTempData2hashMap()
        items.put(type.name, value)
        activationTempData = items.keys.map { "$it=${items.get(it)}" }.joinToString(ActivationTempDataSeparator)
    }

    private fun getActivateTempData2hashMap() : HashMap<String, String> {
        val items = HashMap<String, String>()
        activationTempData?.let {
            it.split(ActivationTempDataSeparator).map {
                val item = it.split("=")
                items.put(item[0], item[1])
            }
        }
        return items
    }
}