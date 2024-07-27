package com.pastebin.service.api.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPaste is a Querydsl query type for Paste
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPaste extends EntityPathBase<Paste> {

    private static final long serialVersionUID = -225139500L;

    public static final QPaste paste = new QPaste("paste");

    public final DateTimePath<org.joda.time.DateTime> createdAt = createDateTime("createdAt", org.joda.time.DateTime.class);

    public final StringPath data = createString("data");

    public final StringPath hash = createString("hash");

    public final BooleanPath isPublic = createBoolean("isPublic");

    public final StringPath language = createString("language");

    public final StringPath title = createString("title");

    public QPaste(String variable) {
        super(Paste.class, forVariable(variable));
    }

    public QPaste(Path<? extends Paste> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPaste(PathMetadata metadata) {
        super(Paste.class, metadata);
    }

}

