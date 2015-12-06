package com.example.kotlin.springboot.mailer

import com.example.kotlin.springboot.AWS
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
open class Mailer4user {
    @Autowired
    private lateinit var manager: AWS.SESManager
}
