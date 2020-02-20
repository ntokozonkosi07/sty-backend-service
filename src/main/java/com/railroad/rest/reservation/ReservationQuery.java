package com.railroad.rest.reservation;

import com.railroad.entity.Reservation;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Transactional
public class ReservationQuery {
    @Inject
    EntityManager entityManager;

    public Reservation createReservation(@NotNull @Valid Reservation reservation){
        return null;
    }
}
