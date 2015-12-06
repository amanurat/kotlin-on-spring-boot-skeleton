package com.example.kotlin.springboot.security.domain.models

import com.fasterxml.jackson.annotation.JsonIgnore
import com.example.kotlin.springboot.domain.models.BaseModel
import com.example.kotlin.springboot.security.RoleType
import org.hibernate.validator.constraints.NotEmpty
import javax.persistence.*

@Entity
@Table(name = "roles")
class Role : BaseModel.WithoutCallBacks {
    @NotEmpty
    @Column(name = "name", nullable = false, unique = true, length = 50)
    private lateinit var _name: String
    val name: String get() = _name

    @JsonIgnore
    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY, cascade = arrayOf(CascadeType.ALL))
    lateinit var users: MutableSet<User>

    constructor()
    constructor(type: RoleType) {
        _name = type.name
    }
}

