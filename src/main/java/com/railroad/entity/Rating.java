package com.railroad.entity;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Collection;

@Entity(name = "S_RATING")
public class Rating extends AbstractEntity {
    @DecimalMin(value = "0.00", message = "rating should be more than zero")
    @DecimalMin(value = "5.00", message = "rating should be less than 5.0")
    private double rateValue;

    @Size(min = 1, max = 280, message = "Character count should be between 1 and 280")
    @NotEmpty(message = "please leave a comment")
    private String comment;

    @OneToMany(mappedBy = "rating")
    private Collection<UserRating> userRatings;

    public double getRateValue() {
        return rateValue;
    }

    public void setRateValue(double rateValue) {
        this.rateValue = rateValue;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Collection<UserRating> getUserRatings() {
        return userRatings;
    }

    public void setUserRatings(Collection<UserRating> userRatings) {
        this.userRatings = userRatings;
    }
}
