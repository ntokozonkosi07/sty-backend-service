package com.railroad.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "S_USER")
@NamedQuery(name=User.FIND_ALL_USERS, query = "select u from User u")
@NamedQuery(name=User.FIND_USER_BY_ID, query = "select u from User u where u.Id = :id")
public class User extends AbstractEntity {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String FIND_ALL_USERS = "user.findAllUsers";
    public static final String FIND_USER_BY_ID = "user.findUserById";

    @NotEmpty(message = "cannot leave name empty")
    private String name;

    @NotEmpty(message = "lastName cannot be empty")
    private String lastName;

    @Email
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "malformed email address")
    @NotNull(message = "email cannot be null")
    @Column(unique = true)
    private String email;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] picture;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Collection<UserRating> userRatings;

    @OneToMany(mappedBy = "artist", fetch = FetchType.EAGER)
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public Collection<UserRating> getUserRatings() {
        return userRatings;
    }

    public void setUserRatings(Collection<UserRating> userRatings) {
        this.userRatings = userRatings;
    }

    public Collection<Reservation> getReservation() {
        return reservation;
    }

    public void setReservation(Collection<Reservation> reservation) {
        this.reservation = reservation;
    }
}
