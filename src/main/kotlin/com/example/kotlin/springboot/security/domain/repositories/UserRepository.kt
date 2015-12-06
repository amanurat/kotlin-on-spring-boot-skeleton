package com.example.kotlin.springboot.security.domain.repositories

import com.example.kotlin.springboot.security.domain.models.User
import org.springframework.data.jpa.repository.JpaRepository
import java.lang.Long
import java.sql.Timestamp

interface UserRepository : JpaRepository<User, Long> {
    fun findByUid(uid: String): User?
}