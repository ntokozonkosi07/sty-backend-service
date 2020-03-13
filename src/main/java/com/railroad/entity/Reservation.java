package com.railroad.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "S_RESERVATION")
@Data
public class Reservation extends AbstractEntity {

    @EmbeddedId
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
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

    @PrePersist
    public void init(){
        this.status = ReservationState.PENDING;
    }
}
