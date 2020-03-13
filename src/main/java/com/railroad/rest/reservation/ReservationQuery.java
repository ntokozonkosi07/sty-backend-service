package com.railroad.rest.reservation;

import com.railroad.entity.Reservation;
import com.railroad.rest.common.Repository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Transactional
public class ReservationQuery extends Repository<Reservation> {
    @Inject
    EntityManager entityManager;

    @Override
    public Collection<Reservation> findAll(int maxResults, int firstResults) {
        return null;
    }

    @Override
    public Reservation findById(Long id) {
        return null;
    }
}
