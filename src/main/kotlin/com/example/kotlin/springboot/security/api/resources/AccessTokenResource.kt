package com.example.kotlin.springboot.security.api.resources

import com.fasterxml.jackson.annotation.JsonCreator
import com.example.kotlin.springboot.api.resources.BaseResource
import com.example.kotlin.springboot.api.resources.BaseResourceAssembler
import com.example.kotlin.springboot.security.api.controllers.AccountController
import com.example.kotlin.springboot.security.domain.models.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.EntityLinks
import org.springframework.hateoas.Link
import org.springframework.hateoas.RelProvider
import org.springframework.hateoas.core.Relation
import org.springframework.stereotype.Service
import java.sql.Timestamp
import java.util.*

@Relation(value = "user", collectionRelation = "users")
open class AccessTokenResource : BaseResource {
    val uid: String
    val accessToken: UUID
    val refreshToken: UUID
    val expiresIn: Timestamp
    val identityId: String
    val cognitToken: String

    @JsonCreator
    constructor(user: User) : super() {
        uid = user.uid
        accessToken = user.accessToken!!.token
        refreshToken = user.accessToken!!.refreshToken
        expiresIn = user.accessToken!!.expiresIn
        identityId = user.accessToken!!.identityId!!
        cognitToken = user.accessToken!!.cognitToken!!
    }

    @Service
    open class Assembler : BaseResourceAssembler<User, AccessTokenResource, AccountController> {
        @Autowired
        constructor(entityLinks: EntityLinks, relProvider: RelProvider) :
        super(entityLinks, relProvider, AccountController::class.java, AccessTokenResource::class.java)

        override fun toResource(entity: User): AccessTokenResource {
            return createResourceWithId(entity.uid, entity)
        }

        override fun linkToSingleResource(entity: User): Link {
            return entityLinks.linkToSingleResource(AccessTokenResource::class.java, entity.uid)
        }

        override fun instantiateResource(entity: User): AccessTokenResource {
            return AccessTokenResource(entity)
        }
    }
}