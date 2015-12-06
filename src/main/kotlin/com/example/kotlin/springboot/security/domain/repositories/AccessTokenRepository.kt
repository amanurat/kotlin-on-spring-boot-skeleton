package com.example.kotlin.springboot.security.domain.repositories

import com.example.kotlin.springboot.security.domain.models.AccessToken
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.lang.Long
import java.sql.Timestamp
import java.util.*

interface AccessTokenRepository : JpaRepository<AccessToken, Long> {
    fun findByToken(token: UUID): AccessToken?
    fun findByRefreshToken(token: UUID): AccessToken?

    @Query("SELECT a FROM AccessToken a WHERE a.token = :token AND a.expiresIn > :timestamp")
    fun findByValidTokenAtDate(@Param("token") token: UUID,
                               @Param("timestamp") timestamp: Timestamp) : AccessToken?
}