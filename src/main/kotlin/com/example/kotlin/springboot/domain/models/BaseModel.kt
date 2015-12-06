package com.example.kotlin.springboot.domain.models

import com.querydsl.jpa.impl.JPAQuery
import com.example.kotlin.springboot.domain.models.extentions.Timestamps
import org.slf4j.LoggerFactory
import java.io.Serializable
import java.sql.Timestamp
import javax.persistence.*

@MappedSuperclass
open class BaseModel : Serializable {
    companion object {
        val serialVersionUID: Long = 1L
        val logger = LoggerFactory.getLogger(BaseModel::class.java)
    }

    @Id @Column(name = "_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long = -1
        private set

    @Version
    protected var _version: Short = -1

    open protected fun isPersisted(): Boolean =  this.id > -1

    /*
     * Callbacks
     */

    @PrePersist
    open protected fun beforeCreate() {
        if(0 > id)
            IllegalArgumentException("${this.javaClass}#id is not set.")

        if(this is Timestamps) {
            val now = Timestamp(System.currentTimeMillis())
            if(this is Timestamps.Created)
                createdAt = now
            if(this is Timestamps.Updated)
                updatedAt = now
        }
    }

    @PreUpdate
    open protected fun beforeUpdate() {
        if(this is Timestamps.Updated)
            updatedAt = Timestamp(System.currentTimeMillis())
    }

    @PreRemove
    open protected fun beforeRemove() { }

    @PostLoad
    open protected fun afterLoad() { }

    @PostPersist
    open protected fun afterInsert() { }

    @PostUpdate
    open protected fun afterUpdate() { }

    @PostRemove
    open protected fun afterDelete() { }

    @MappedSuperclass
    open class WithoutCallBacks : BaseModel() {
        override fun beforeCreate() {}
        override fun beforeUpdate() {}
        override fun beforeRemove() {}
        override fun afterLoad() {}
        override fun afterInsert() {}
        override fun afterUpdate() {}
        override fun afterDelete() {}
    }
}
