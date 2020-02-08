package com.railroad.common.producers;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@RequestScoped
public class EntityManagerProducer {
    @Produces
    @PersistenceContext
    EntityManager entityManager;
}
