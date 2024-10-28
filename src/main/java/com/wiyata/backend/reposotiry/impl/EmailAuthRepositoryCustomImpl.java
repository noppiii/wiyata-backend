package com.wiyata.backend.reposotiry.impl;

import com.wiyata.backend.model.EmailAuth;
import com.wiyata.backend.reposotiry.EmailAuthRepositoryCustom;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.time.LocalDateTime;
import java.util.Optional;

public class EmailAuthRepositoryCustomImpl implements EmailAuthRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<EmailAuth> findValidAuthByEmail(String email, String authToken, LocalDateTime now) {
        String jpql = "SELECT e FROM EmailAuth e WHERE e.email = :email AND e.authToken = :authToken AND e.expireDate > :now AND e.expired = false";
        TypedQuery<EmailAuth> query = entityManager.createQuery(jpql, EmailAuth.class);
        query.setParameter("email", email);
        query.setParameter("authToken", authToken);
        query.setParameter("now", now);

        return query.getResultStream().findFirst();
    }
}
