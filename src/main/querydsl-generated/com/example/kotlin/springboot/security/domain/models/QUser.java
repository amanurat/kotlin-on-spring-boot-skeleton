package com.example.kotlin.springboot.security.domain.models;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.example.kotlin.springboot.domain.models.QBaseModel;
import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -476266741L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUser user = new QUser("user");

    public final QBaseModel _super = new QBaseModel(this);

    public final StringPath _password = createString("_password");

    public final StringPath _type = createString("_type");

    //inherited
    public final NumberPath<Short> _version = _super._version;

    public final QAccessToken accessToken;

    public final MapPath<String, String, StringPath> activateTempData2hashMap = this.<String, String, StringPath>createMap("activateTempData2hashMap", String.class, String.class, StringPath.class);

    public final StringPath activationCode = createString("activationCode");

    public final DateTimePath<java.sql.Timestamp> activationExpiresIn = createDateTime("activationExpiresIn", java.sql.Timestamp.class);

    public final StringPath activationTempData = createString("activationTempData");

    public final DateTimePath<java.sql.Timestamp> createdAt = createDateTime("createdAt", java.sql.Timestamp.class);

    public final DateTimePath<java.sql.Timestamp> deletedAt = createDateTime("deletedAt", java.sql.Timestamp.class);

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath loginId = createString("loginId");

    public final StringPath password = createString("password");

    //inherited
    public final BooleanPath persisted = _super.persisted;

    public final SetPath<Role, QRole> roles = this.<Role, QRole>createSet("roles", Role.class, QRole.class, PathInits.DIRECT2);

    public final StringPath uid = createString("uid");

    public final DateTimePath<java.sql.Timestamp> updatedAt = createDateTime("updatedAt", java.sql.Timestamp.class);

    public QUser(String variable) {
        this(User.class, forVariable(variable), INITS);
    }

    public QUser(Path<User> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QUser(PathMetadata metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QUser(PathMetadata metadata, PathInits inits) {
        this(User.class, metadata, inits);
    }

    public QUser(Class<? extends User> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.accessToken = inits.isInitialized("accessToken") ? new QAccessToken(forProperty("accessToken"), inits.get("accessToken")) : null;
    }

}

