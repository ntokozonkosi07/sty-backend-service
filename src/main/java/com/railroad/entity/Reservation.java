package com.railroad.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "S_RESERVATION")
public class Reservation extends AbstractEntity {
    @EmbeddedId
    private ReservationKey id;

    @ManyToOne
    @MapsId("clientId")
    private User client;

    @ManyToOne
    @MapsId("artistId")
    private User artist;

    @NotNull
    private LocalDateTime datTimeFrom;

    @NotNull
    private LocalDateTime datTimeTo;

    private ReservationState status;

    @ManyToOne
    @JoinColumn(name = "Service_Id")
    private ServiceProvided serviceProvided;

    public Reservation() {
    }

    @PrePersist
    public void init(){
        this.status = ReservationState.PENDING;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public User getArtist() {
        return artist;
    }

    public void setArtist(User artist) {
        this.artist = artist;
    }

    public LocalDateTime getDatTimeFrom() {
        return datTimeFrom;
    }

    public void setDatTimeFrom(LocalDateTime datTimeFrom) {
        this.datTimeFrom = datTimeFrom;
    }

    public LocalDateTime getDatTimeTo() {
        return datTimeTo;
    }

    public void setDatTimeTo(LocalDateTime datTimeTo) {
        this.datTimeTo = datTimeTo;
    }

    public ReservationState getStatus() {
        return status;
    }

    public void setStatus(ReservationState status) {
        this.status = status;
    }

    public ServiceProvided getServiceProvided() {
        return serviceProvided;
    }

    public void setServiceProvided(ServiceProvided serviceProvided) {
        this.serviceProvided = serviceProvided;
    }
}
