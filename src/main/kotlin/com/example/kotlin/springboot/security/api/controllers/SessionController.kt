package com.example.kotlin.springboot.security.api.controllers

import com.example.kotlin.springboot.error.AppError
import com.example.kotlin.springboot.error.Errors
import com.example.kotlin.springboot.security.RoleType
import com.example.kotlin.springboot.security.domain.models.User
import com.example.kotlin.springboot.security.domain.services.AccessTokenService
import com.example.kotlin.springboot.security.domain.services.UserService
import com.example.kotlin.springboot.security.api.resources.AccessTokenResource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

import com.example.kotlin.springboot.security.Constants as Cnst

@RestController
@RequestMapping("/session")
class SessionController {
    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var tokenService: AccessTokenService

    @Autowired
    lateinit var accessTokenResourceAssembler: AccessTokenResource.Assembler

    @RequestMapping(method = arrayOf(RequestMethod.POST))
    fun create(@RequestParam("refreshToken", required = false) refreshToken: String?,
               @RequestParam("login_id", required = false) loginID: String?,
               @RequestParam("password", required = false) password: String?) : ResponseEntity<AccessTokenResource> {

        val user = if (null != loginID && null != password) {
            userService.authenticate(RoleType.User, loginID, password)?.
                    let { userService.resetToken(it) }
                    ?: throw AppError(Errors.UserFailedToAuthentication, null)
        } else {
            val token = try {
                UUID.fromString(refreshToken)
            } catch(ex: IllegalArgumentException) {
                throw AppError(Errors.Unauthorized, null)
            }
            userService.resetToken(token)
        }

        return ResponseEntity.ok(accessTokenResourceAssembler.toResource(user))
    }

    @RequestMapping(method = arrayOf(RequestMethod.DELETE))
    fun destroy(@ModelAttribute(com.example.kotlin.springboot.security.Constants.CurrentUser) currentUser: User) : ResponseEntity<Any?> {
        tokenService.invalidate(currentUser.accessToken!!)
        return ResponseEntity(null, HttpStatus.NO_CONTENT)
    }
}