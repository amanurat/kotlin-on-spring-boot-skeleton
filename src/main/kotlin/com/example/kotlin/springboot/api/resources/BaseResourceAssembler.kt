package com.example.kotlin.springboot.api.resources

import org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo
import org.springframework.hateoas.*
import org.springframework.hateoas.core.EmbeddedWrapper
import org.springframework.hateoas.core.EmbeddedWrappers
import org.springframework.hateoas.mvc.ResourceAssemblerSupport

abstract class BaseResourceAssembler<T, D : ResourceSupport, C>(
        protected val entityLinks: EntityLinks, protected val relProvider: RelProvider,
        protected val controllerClass: Class<C>, resourceType: Class<D>) :
        ResourceAssemblerSupport<T, D>(controllerClass, resourceType) {

    open fun toEmbeddable(entities: Iterable<T>): List<EmbeddedWrapper> {
        val wrapper = EmbeddedWrappers(true) // Prefer collection
        val resources = toResources(entities)
        return resources.map({ a -> wrapper.wrap(a) })
    }

    open fun toEmbeddable(entity: T): EmbeddedWrapper {
        val wrapper = EmbeddedWrappers(false) // DO NOT prefer collections
        val resource = toResource(entity)
        return wrapper.wrap(resource)
    }

    open fun toEmbeddedList(entities: Iterable<T>): Resources<D> {
        val resources = toResources(entities)
        return Resources(resources, linkTo(controllerClass).withSelfRel()) // Add self link to list endpoint
    }

    abstract fun linkToSingleResource(entity: T): Link

    override abstract fun instantiateResource(entity: T): D
}
