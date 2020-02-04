package com.railroad.common.producers;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class EntityManagerProducer {
    @Produces
    @PersistenceContext
    EntityManager entityManager;
}
