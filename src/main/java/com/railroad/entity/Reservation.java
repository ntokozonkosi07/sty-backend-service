package com.railroad.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "S_RESERVATION")
public class Reservation extends AbstractEntity {
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "artist_id")
    private Artist artist;

    @NotNull
    private LocalDateTime datTimeFrom;

    @NotNull
    private LocalDateTime datTimeTo;

    private ReservationState status;

    @OneToOne
    @JoinColumn(name = "Service_Id")
    private Service service;

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
}
