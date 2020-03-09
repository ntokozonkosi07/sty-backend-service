package com.railroad.entity;

import com.railroad.entity.serviceProvided.ServiceProvided;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "S_ARTIST")
public class Artist extends User {

	private static final long serialVersionUID = 1L;

    @OneToMany(mappedBy = "artist")
    private Collection<Reservation> reservations;

    public Collection<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Collection<Reservation> reservations) {
        this.reservations = reservations;
    }
}
