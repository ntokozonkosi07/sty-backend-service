package com.railroad.entity.reservation;

import com.railroad.entity.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "S_RESERVATION")
@Data
@NamedQuery(name = Reservation.FIND_RESERVATION_BY_ID, query = "SELECT r FROM Reservation r where r.id = :id")
@NamedQuery(name = Reservation.FIND_ALL_RESERVATIONS, query = "SELECT r FROM Reservation r")
@NamedQuery(name = Reservation.FIND_RESERVATION_BY_ARTIST_ID, query = "SELECT r FROM Reservation r INNER JOIN r.artist a WHERE a.id = :id")
@NamedQuery(name = Reservation.FIND_RESERVATION_BY_CLIENT_ID, query = "SELECT r FROM Reservation r INNER JOIN r.client c WHERE c.id = :id")
public class Reservation extends AbstractEntity {

    public static final String FIND_RESERVATION_BY_ID = "RESERVATION.FIND_RESERVATION_BY_ID";
    public static final String FIND_ALL_RESERVATIONS = "RESERVATION.FIND_ALL_RESERVATIONS";
    public static final String FIND_RESERVATION_BY_ARTIST_ID = "RESERVATION.FIND_RESERVATION_BY_ARTIST_ID";
    public static final String FIND_RESERVATION_BY_CLIENT_ID = "RESERVATION.FIND_RESERVATION_BY_CLIENT_ID";

    @EmbeddedId
    @Getter(AccessLevel.NONE)
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

    @Column(length = 140)
    private String note;

    @PrePersist
    public void init(){
        this.status = ReservationState.PENDING;
    }
}
