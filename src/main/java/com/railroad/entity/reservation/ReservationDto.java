package com.railroad.entity.reservation;

import com.railroad.entity.ServiceProvided;
import com.railroad.entity.User;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class ReservationDto {

    @NotNull
    private Long clientId;

    @NotNull
    private User artistId;

    @NotNull
    private LocalDateTime datTimeFrom;

    @NotNull
    private LocalDateTime datTimeTo;

    @NotNull
    private ReservationState status;

    private ServiceProvided serviceProvided;
}
