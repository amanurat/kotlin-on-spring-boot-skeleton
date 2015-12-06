package com.example.kotlin.springboot.api.resources

import com.fasterxml.jackson.annotation.JsonUnwrapped
import org.springframework.hateoas.ResourceSupport
import org.springframework.hateoas.Resources
import org.springframework.hateoas.core.EmbeddedWrapper

abstract class BaseResource : ResourceSupport() {
    @JsonUnwrapped
    var embeddeds: Resources<EmbeddedWrapper>? = null
}