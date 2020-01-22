package com.railroad.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;

@Entity
@Table(name = "S_USER")
@NamedQuery(name=User.FIND_ALL_USERS, query = "select u from User u")
@NamedQuery(name=User.FIND_USER_BY_ID, query = "select u from User u where u.Id = :id")
public class User extends AbstractEntity {

    public static final String FIND_ALL_USERS = "user.findAllUsers";
    public static final String FIND_USER_BY_ID = "user.findUserById";

    @NotEmpty(message = "cannot leave name empty")
    private String name;

    @NotEmpty(message = "lastName cannot be empty")
    private String lastName;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] picture;

    @ManyToMany
    @JoinTable(
            name = "s_user_rating",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "RATING_ID")
    )
    private Collection<Rating> ratings;

    @OneToMany
    private Collection<Reservation> reservation;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public Collection<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(Collection<Rating> ratings) {
        this.ratings = ratings;
    }

    public Collection<Reservation> getReservation() {
        return reservation;
    }

    public void setReservation(Collection<Reservation> reservation) {
        this.reservation = reservation;
    }
}
