package com.example.kotlin.springboot.security.domain.services

import com.example.kotlin.springboot.security.RoleType
import com.example.kotlin.springboot.security.domain.models.Role
import com.example.kotlin.springboot.security.domain.repositories.RoleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service @Transactional(rollbackFor = arrayOf(Exception::class))
open class RoleService {

    @Autowired
    open lateinit var roleRepo: RoleRepository


    fun findOrCreateByType(type: RoleType) : Role {
        return roleRepo.findBy_name(type.name)?.
                let { it }
                ?: roleRepo.save(Role(type))
    }
}