package com.example.kotlin.springboot.security.config

import com.example.kotlin.springboot.error.AppError
import com.example.kotlin.springboot.error.Errors
import com.example.kotlin.springboot.security.domain.models.User
import com.example.kotlin.springboot.security.domain.services.AccessTokenService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import java.sql.Timestamp
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import com.example.kotlin.springboot.security.Constants as Cnst

class AuthorizeInterceptor : HandlerInterceptor {

    @Autowired
    lateinit var tokenService: AccessTokenService

    override fun preHandle(request: HttpServletRequest?, response: HttpServletResponse?, handler: Any?): Boolean {
        request?.let {
            // ignore
            when( Pair(it.method, it.requestURI) ){
                Pair("POST", "/account"),
                Pair("POST", "/session") -> return true
            }

            val request = it
            var currentUser: User? = null
            it.getHeader("Authorization")?.let {
                val accessToken = it.split(" ")
                if("Bearer".equals(accessToken[0]) && accessToken[1].isNotBlank()) {
                    val token =try {
                        UUID.fromString(accessToken[1])
                    } catch(ex: IllegalArgumentException) {
                        throw AppError(Errors.Unauthorized, null)
                    }
                    val now = Timestamp(System.currentTimeMillis())
                    currentUser = tokenService.findByValidTokenAtDate(token, now)?.user
                }
            }
            currentUser?.let {
                request.setAttribute(Cnst.CurrentUser, it)
            } ?: AppError(Errors.Unauthorized, null)
        }
        return true
    }

    override fun postHandle(request: HttpServletRequest?, response: HttpServletResponse?, handler: Any?, modelAndView: ModelAndView?) {
    }

    override fun afterCompletion(request: HttpServletRequest?, response: HttpServletResponse?, handler: Any?, ex: Exception?) {
    }
}