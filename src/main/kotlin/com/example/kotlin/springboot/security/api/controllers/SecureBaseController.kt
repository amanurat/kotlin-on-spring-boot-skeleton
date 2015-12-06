package com.example.kotlin.springboot.security.api.controllers

import com.example.kotlin.springboot.security.domain.models.User
import org.springframework.web.bind.annotation.ModelAttribute
import javax.servlet.http.HttpServletRequest

import com.example.kotlin.springboot.security.Constants as Cnst

open class SecureBaseController {
    @ModelAttribute(com.example.kotlin.springboot.security.Constants.CurrentUser)
    open fun getCurrentUser(request: HttpServletRequest) : User? {
        return request.getAttribute(com.example.kotlin.springboot.security.Constants.CurrentUser) as User?
    }
}