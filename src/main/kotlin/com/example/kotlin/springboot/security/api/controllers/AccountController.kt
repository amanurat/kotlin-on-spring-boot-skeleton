package com.example.kotlin.springboot.security.api.controllers

import com.example.kotlin.springboot.error.AppError
import com.example.kotlin.springboot.error.Errors
import com.example.kotlin.springboot.security.mailer.Mailer4security
import com.example.kotlin.springboot.security.RoleType
import com.example.kotlin.springboot.security.api.resources.AccessTokenResource
import com.example.kotlin.springboot.security.domain.models.User
import com.example.kotlin.springboot.security.domain.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import com.example.kotlin.springboot.security.Constants as Cnst

@RestController
@RequestMapping("/account")
class AccountController : SecureBaseController() {
    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var mailService: Mailer4security

    @Autowired
    lateinit var accessTokenResourceAssembler: AccessTokenResource.Assembler

    @RequestMapping(method = arrayOf(RequestMethod.POST))
    fun create(@RequestParam("login_id", required = true) loginId: String,
               @RequestParam("password", required = true) password: String) : ResponseEntity<AccessTokenResource> {
        userService.findByLoginID(RoleType.User, loginId)?.let {
            throw AppError(Errors.DataDuplicate, null, "login_id=($loginId)")
        }
        val user = userService.create(RoleType.User, loginId, password)
        return ResponseEntity(accessTokenResourceAssembler.toResource(user), HttpStatus.CREATED)
    }

    @RequestMapping(method = arrayOf(RequestMethod.PATCH))
    fun update(@ModelAttribute(com.example.kotlin.springboot.security.Constants.CurrentUser) currentUser: User,
               @RequestParam("login_id", required = false) loginID: String?,
               @RequestParam("password", required = false) password: String?) : ResponseEntity<Any?> {

        userService.safetyUpdate(currentUser) { user ->
            loginID?.let {
                if (it.isNotEmpty()) {
                    userService.findByLoginID(RoleType.User, it)?.let {
                        throw AppError(Errors.DataDuplicate, null, "login_id=($loginID)")
                    }
                    user.addActivateTempData(User.ColumnType.LoginID, it)
                }
            }
            password?.let {
                if (it.isNotEmpty())
                    user.addActivateTempData(User.ColumnType.Password, it)
            }
        }

        currentUser.activationCode?.let {
            val to = loginID?.let { it } ?: currentUser.loginId
            mailService.sendActivationCode(it, to)
        }
        return ResponseEntity(null, HttpStatus.NO_CONTENT)
    }

    @RequestMapping(method = arrayOf(RequestMethod.DELETE))
    fun destroy(@ModelAttribute(com.example.kotlin.springboot.security.Constants.CurrentUser) currentUser: User) : ResponseEntity<Any?> {
        userService.softDestroy(currentUser)
        return ResponseEntity(null, HttpStatus.NO_CONTENT)
    }

    @RequestMapping("/activate", method = arrayOf(RequestMethod.PATCH))
    fun activate(@ModelAttribute(com.example.kotlin.springboot.security.Constants.CurrentUser) currentUser: User,
                 @RequestParam("code", required = true) code: String) : ResponseEntity<Any?> {
        userService.activate(currentUser, code)
        return ResponseEntity(null, HttpStatus.ACCEPTED)
    }
}