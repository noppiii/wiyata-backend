package com.wiyata.backend.model;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.*;

import java.time.LocalDateTime;

public class QEmailAuth extends EntityPathBase<EmailAuth> {

    private static final long serialVersionUID = 1L;

    public static final QEmailAuth emailAuth = new QEmailAuth("emailAuth");

    public final NumberPath<Long> id = createNumber("id", Long.class);
    public final StringPath email = createString("email");
    public final StringPath authToken = createString("authToken");
    public final BooleanPath expired = createBoolean("expired");
    public final DateTimePath<LocalDateTime> expireDate = createDateTime("expireDate", LocalDateTime.class);

    public QEmailAuth(String variable) {
        super(EmailAuth.class, forVariable(variable));
    }

    public QEmailAuth(Path<? extends EmailAuth> path) {
        super(path.getType(), path.getMetadata());
    }

    public QEmailAuth(PathMetadata metadata) {
        super(EmailAuth.class, metadata);
    }
}
