package com.railroad.rest.reservation;

import com.railroad.entity.ReservationKey;
import com.railroad.entity.ServiceProvided;
import com.railroad.entity.User;
import com.railroad.entity.reservation.Reservation;
import com.railroad.entity.reservation.ReservationDto;
import com.railroad.entity.reservation.ReservationState;
import com.railroad.rest.common.AbstractService;
import com.railroad.rest.serviceProvided.ServiceProvidedService;
import com.railroad.rest.user.UserService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Collection;

@Stateless
public class ReservationService extends AbstractService {

    @Inject
    private ReservationQuery rq;

    @Inject
    private UserService userService;

    @Inject
    private ServiceProvidedService serviceProvidedService;

    public Reservation findById(Long id){
        return rq.findById(id);
    }

    public Collection<Reservation> findReservationsByClientId(Long id, int maxResults, int firstResults){
        return rq.findReservationsByClientId(id, maxResults, firstResults);
    }

    public Collection<Reservation> findReservationsByArtistd(Long id, int maxResults, int firstResults){
        return rq.findtReservationsByArtistId(id, maxResults, firstResults);
    }

    public Collection<Reservation> findAll(int maxResults, int firstResults){
        return rq.findAll(maxResults,firstResults);
    }

    public Reservation bookAppointment(ReservationDto reservation){
        User client = userService.findUserById(reservation.getClientId());
        User artist = userService.findUserById(reservation.getArtistId());
        ServiceProvided serviceProvided = serviceProvidedService.findById(reservation.getServiceProvidedId());

        Reservation _reservation = new Reservation();
        _reservation.setClient(client);
        _reservation.setArtist(artist);
        _reservation.setServiceProvided(serviceProvided);
        _reservation.setNote(reservation.getNote());
        _reservation.setStartDateTime(reservation.getStartDateTime());
        _reservation.setEndDateTime(reservation.getStartDateTime().plusHours(1));
        _reservation.setStatus(ReservationState.PENDING);

            return rq.save(_reservation);
    }
}
