package com.railroad.entity;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "S_ARTIST")
public class Artist extends User {

	private static final long serialVersionUID = 1L;

	@ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name = "ARTIST_ID"),
            inverseJoinColumns = @JoinColumn(name = "SERVICE_ID")
    )
    private Collection<Service> services;

    @OneToMany
    private Collection<Reservation> reservations;

    public Collection<Service> getServices() {
        return services;
    }

    public void setServices(Collection<Service> services) {
        this.services = services;
    }

    public Collection<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Collection<Reservation> reservations) {
        this.reservations = reservations;
    }
}
