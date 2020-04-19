package com.railroad.entity;

import lombok.Data;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "S_RATING")
@NamedQuery(name = UserRating.FIND_ALL_USER_RATINGS, query = "SELECT ur FROM UserRating ur")
@NamedQuery(name = UserRating.FIND_USER_RATING_BY_RATEE_ID, query = "SELECT ur FROM UserRating ur WHERE ur.id.rateeId = :id")
@NamedQuery(name = UserRating.FIND_USER_RATING_BY_ID, query = "SELECT ur FROM UserRating ur WHERE ur.rating.Id = :id")
public class UserRating implements Serializable {

    public static final String FIND_ALL_USER_RATINGS = "USER_RATING.FIND_ALL_USER_RATINGS";
    public static final String FIND_USER_RATING_BY_RATEE_ID = "USER_RATING.FIND_USER_RATING_BY_USER_ID";
    public static final String FIND_USER_RATING_BY_ID = "USER_RATING.FIND_USER_RATING_BY_ID";

    @EmbeddedId
    private UserRatingKey id;
 
    @ManyToOne
    @MapsId("raterId")
    @NotNull(message = "rater cannot be null")
    private User rater;

    @ManyToOne
    @MapsId("rateeId")
    @NotNull(message = "ratee cannot be null")
    private User ratee;
    
    @ManyToOne
    @NotNull(message = "rating cannot be null")
    private Rating rating;
}
