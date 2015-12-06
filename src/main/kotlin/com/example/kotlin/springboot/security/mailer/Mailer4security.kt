package com.example.kotlin.springboot.security.mailer

import com.example.kotlin.springboot.AWS
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
open class Mailer4security {
    @Autowired
    private lateinit var manager: AWS.SESManager

    fun sendActivationCode(code: String, to: String) {
        manager.sendMessage(listOf(to), "activation code", code)
    }
}