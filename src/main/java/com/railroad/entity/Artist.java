package com.railroad.entity;

import com.railroad.entity.serviceProvided.ServiceProvided;

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
    private Collection<ServiceProvided> serviceUtilities;

    @OneToMany(mappedBy = "artist")
    private Collection<Reservation> reservations;

    public Collection<ServiceProvided> getServiceUtilities() {
        return serviceUtilities;
    }

    public void setServiceUtilities(Collection<ServiceProvided> serviceUtilities) {
        this.serviceUtilities = serviceUtilities;
    }

    public Collection<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Collection<Reservation> reservations) {
        this.reservations = reservations;
    }
}
