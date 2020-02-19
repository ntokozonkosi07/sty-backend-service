package com.railroad.entity;

import com.railroad.entity.User;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Collection;

@Entity(name = "S_CLIENT")
public class Client extends User {
    @OneToMany(mappedBy = "client")
    private Collection<Reservation> reservations;

    public Collection<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Collection<Reservation> reservations) {
        this.reservations = reservations;
    }
}
