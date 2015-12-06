package com.example.kotlin.springboot.security.domain.models;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.example.kotlin.springboot.domain.models.QBaseModel_WithoutCallBacks;
import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRole is a Querydsl query type for Role
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QRole extends EntityPathBase<Role> {

    private static final long serialVersionUID = -476359754L;

    public static final QRole role = new QRole("role");

    public final QBaseModel_WithoutCallBacks _super = new QBaseModel_WithoutCallBacks(this);

    public final StringPath _name = createString("_name");

    //inherited
    public final NumberPath<Short> _version = _super._version;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath name = createString("name");

    //inherited
    public final BooleanPath persisted = _super.persisted;

    public final SetPath<User, QUser> users = this.<User, QUser>createSet("users", User.class, QUser.class, PathInits.DIRECT2);

    public QRole(String variable) {
        super(Role.class, forVariable(variable));
    }

    public QRole(Path<Role> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRole(PathMetadata metadata) {
        super(Role.class, metadata);
    }

}

