package com.railroad.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "S_RESERVATION")
@Data
@NamedQuery(name = Reservation.FIND_RESERVATION_BY_ID, query = "SELECT r FROM Reservation r where r.id = :id")
@NamedQuery(name = Reservation.FIND_ALL_RESERVATIONS, query = "SELECT r FROM Reservation r")
@NamedQuery(name = Reservation.FIND_RESERVATION_BY_ARTIST_ID, query = "SELECT r FROM Reservation r INNER JOIN r.artist a WHERE a.id = :id")
@NamedQuery(name = Reservation.FIND_RESERVATION_BY_CLIENT_ID, query = "SELECT r FROM Reservation r INNER JOIN r.client c WHERE c.id = :id")
public class Reservation extends AbstractEntity{

    public static final String FIND_RESERVATION_BY_ID = "RESERVATION.FIND_RESERVATION_BY_ID";
    public static final String FIND_ALL_RESERVATIONS = "RESERVATION.FIND_ALL_RESERVATIONS";
    public static final String FIND_RESERVATION_BY_ARTIST_ID = "RESERVATION.FIND_RESERVATION_BY_ARTIST_ID";
    public static final String FIND_RESERVATION_BY_CLIENT_ID = "RESERVATION.FIND_RESERVATION_BY_CLIENT_ID";

    @ManyToOne
    private User client;

    @ManyToOne
    private User artist;

    @NotNull
    @FutureOrPresent(message = "Appointment date cannot be in the past")
    private LocalDateTime startDateTime;

    @NotNull
    private LocalDateTime endDateTime;

    private ReservationState status;

    @ManyToOne
    @JoinColumn(name = "Service_Id")
    private ServiceProvided serviceProvided;

    @Column(length = 140)
    private String note;

    @PrePersist
    public void init(){
        this.status = ReservationState.PENDING;
    }
}
