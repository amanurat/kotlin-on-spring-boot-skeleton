package com.example.kotlin.springboot.security.domain.repositories

import com.example.kotlin.springboot.security.domain.models.Role
import org.springframework.data.jpa.repository.JpaRepository
import java.lang.Short

interface  RoleRepository : JpaRepository<Role, Short> {
    fun findBy_name(name: String): Role?
}