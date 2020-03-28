package com.railroad.rest.reservation;

import com.railroad.entity.reservation.Reservation;
import com.railroad.rest.common.AbstractService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Collection;

@Stateless
public class ReservationService extends AbstractService {

    @Inject
    private ReservationQuery rq;

    public Reservation findById(Long id){
        return rq.findById(id);
    }

    public Collection<Reservation> findReservationsByClientId(Long id, int maxResults, int firstResults){
        return rq.findReservationsByClientId(id, maxResults, firstResults);
    }

    public Collection<Reservation> findReservationsByArtistd(Long id, int maxResults, int firstResults){
        return rq.findtReservationsByArtistId(id, maxResults, firstResults);
    }

}
