package com.railroad.entity.reservation;

import com.railroad.entity.ServiceProvided;
import com.railroad.entity.User;
import lombok.Data;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.DefaultValue;
import java.time.LocalDateTime;

@Data
public class ReservationDto {

    @NotNull
    private Long clientId;

    @NotNull
    private Long artistId;

    @NotNull
    @JsonbDateFormat(value = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime startDateTime;

    @Size(min=0, max = 140)
    @DefaultValue(value = "")
    private String note;

    private Long serviceProvidedId;
}
