package com.railroad.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Table(name = "S_RESERVATION")
public class Reservation extends AbstractEntity {
    @EmbeddedId
    private ReservationKey id;

    @ManyToOne
    @MapsId("clientId")
    private Client client;

    @ManyToOne
    @MapsId("artistId")
    private Artist artist;

    @NotNull
    private LocalDateTime datTimeFrom;

    @NotNull
    private LocalDateTime datTimeTo;

    private ReservationState status;

    @ManyToOne
    @JoinColumn(name = "Service_Id")
    private Collection<ServiceUtility> serviceUtility;

    public Reservation() {
    }

    @PrePersist
    public void init(){
        this.status = ReservationState.PENDING;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
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

    public Collection<ServiceUtility> getServiceUtility() {
        return serviceUtility;
    }

    public void setServiceUtility(Collection<ServiceUtility> serviceUtility) {
        this.serviceUtility = serviceUtility;
    }
}
