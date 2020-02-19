package com.railroad.entity;

import com.railroad.entity.User;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "S_RATING")
public class UserRating implements Serializable {

	@EmbeddedId
    private UserRatingKey id;
 
    @ManyToOne
    @MapsId("userId")
    @NotNull(message = "user cannot be null")
    private User user;
    
    @ManyToOne
    @MapsId("ratingId")
    @NotNull(message = "rating cannot be null")
    private Rating rating;


    public UserRatingKey getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Rating getUserRating() {
        return rating;
    }

    public void setUserRating(Rating userRating) {
        this.rating = userRating;
    }
}
