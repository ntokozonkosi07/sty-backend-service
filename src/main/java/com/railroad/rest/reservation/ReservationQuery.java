package com.railroad.rest.reservation;

import com.railroad.entity.Reservation;
import com.railroad.rest.common.Repository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.HashMap;

@Transactional
public class ReservationQuery extends Repository<Reservation> {

    public ReservationQuery() {
        super(Reservation.class);
    }

    @Override
    public Collection<Reservation> findAll(int maxResults, int firstResults) {
        return this.getCollectionResults(maxResults, firstResults, Reservation.FIND_ALL_RESERVATIONS);
    }

    @Override
    public Reservation findById(Long id) {
    return this.getSingleResultByNamedQuery(new HashMap<String, Object>() {{ put("id", Long.valueOf(id)); }},Reservation.FIND_RESERVATION_BY_ID);
    }

    public Collection<Reservation> findtReservationsByArtistId(Long id, int maxResults, int firstResults){
        return this.getCollectionResults(new HashMap<String, Object>() {{ put("id", Long.valueOf(id)); }}, maxResults, firstResults, Reservation.FIND_RESERVATION_BY_ARTIST_ID);
    }

    public Collection<Reservation> findReservationsByClientId(Long id, int maxResults, int firstResults){
        return this.getCollectionResults(new HashMap<String, Object>() {{ put("id", Long.valueOf(id)); }}, maxResults, firstResults, Reservation.FIND_RESERVATION_BY_CLIENT_ID);
    }
}
