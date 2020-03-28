package com.railroad.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Collection;

@Data
@Entity(name = "S_RATING")
public class Rating extends AbstractEntity {
    @DecimalMin(value = "0.00", message = "rating should be more than zero")
    @DecimalMin(value = "5.00", message = "rating should be less than 5.0")
    private double rateValue;

    @Size(min = 1, max = 280, message = "Character count should be between 1 and 280")
    @NotEmpty(message = "please leave a comment")
    private String comment;
}
