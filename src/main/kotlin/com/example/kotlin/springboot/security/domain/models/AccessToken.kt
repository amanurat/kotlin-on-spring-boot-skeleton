package com.example.kotlin.springboot.security.domain.models

import com.fasterxml.jackson.annotation.JsonIgnore
import com.example.kotlin.springboot.util.UUIDToBytesConverter
import java.sql.Timestamp
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "access_tokens")
class AccessToken {
    @Id @Column(name = "user_id")
    var id: Long = -1
        private set

    @Version
    protected var _version: Short = -1

    lateinit var expiresIn: Timestamp

    @Column(name = "token", unique = true, columnDefinition = "BINARY")
    @Convert(converter = UUIDToBytesConverter::class)
    lateinit var token: UUID

    @Column(name = "refresh_token", unique = true, columnDefinition = "BINARY")
    @Convert(converter = UUIDToBytesConverter::class)
    lateinit var refreshToken: UUID

    @OneToOne(fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn
    @JsonIgnore
    lateinit var user: User

    @Transient
    var cognitToken: String? = null

    @Transient
    var identityId: String? = null

    constructor()
    constructor(user: User) {
        this.user = user
        this.id = user.id
    }

    fun reset() : AccessToken {
        this.expiresIn = Timestamp(System.currentTimeMillis() + (24 * 60 * 60 - 10) * 1000)
        this.refreshToken = UUID.randomUUID()
        this.token = UUID.randomUUID()
        return this
    }
}