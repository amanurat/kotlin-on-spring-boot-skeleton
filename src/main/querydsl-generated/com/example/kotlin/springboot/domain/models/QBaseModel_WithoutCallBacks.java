package com.example.kotlin.springboot.domain.models;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QBaseModel_WithoutCallBacks is a Querydsl query type for WithoutCallBacks
 */
@Generated("com.querydsl.codegen.SupertypeSerializer")
public class QBaseModel_WithoutCallBacks extends EntityPathBase<BaseModel.WithoutCallBacks> {

    private static final long serialVersionUID = 1791854814L;

    public static final QBaseModel_WithoutCallBacks withoutCallBacks = new QBaseModel_WithoutCallBacks("withoutCallBacks");

    public final QBaseModel _super = new QBaseModel(this);

    //inherited
    public final NumberPath<Short> _version = _super._version;

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final BooleanPath persisted = _super.persisted;

    public QBaseModel_WithoutCallBacks(String variable) {
        super(BaseModel.WithoutCallBacks.class, forVariable(variable));
    }

    public QBaseModel_WithoutCallBacks(Path<? extends BaseModel.WithoutCallBacks> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBaseModel_WithoutCallBacks(PathMetadata metadata) {
        super(BaseModel.WithoutCallBacks.class, metadata);
    }

}

