package com.example.kotlin.springboot.security.domain.models;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAccessToken is a Querydsl query type for AccessToken
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAccessToken extends EntityPathBase<AccessToken> {

    private static final long serialVersionUID = -1021585483L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAccessToken accessToken = new QAccessToken("accessToken");

    public final NumberPath<Short> _version = createNumber("_version", Short.class);

    public final DateTimePath<java.sql.Timestamp> expiresIn = createDateTime("expiresIn", java.sql.Timestamp.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ComparablePath<java.util.UUID> refreshToken = createComparable("refreshToken", java.util.UUID.class);

    public final ComparablePath<java.util.UUID> token = createComparable("token", java.util.UUID.class);

    public final QUser user;

    public QAccessToken(String variable) {
        this(AccessToken.class, forVariable(variable), INITS);
    }

    public QAccessToken(Path<AccessToken> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QAccessToken(PathMetadata metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QAccessToken(PathMetadata metadata, PathInits inits) {
        this(AccessToken.class, metadata, inits);
    }

    public QAccessToken(Class<? extends AccessToken> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

