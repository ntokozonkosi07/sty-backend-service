package com.railroad.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class UserRatingKey implements Serializable {
	
	@Column(name = "rater_id")
    Long raterId;

    @Column(name = "ratee_id")
    Long rateeId;
 
    @Column(name = "rating_id")
    Long ratingId;
}
