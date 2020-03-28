package com.railroad.entity;

import com.railroad.entity.reservation.Reservation;
import lombok.Data;

import javax.json.bind.annotation.JsonbProperty;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Collection;

@Entity
@Table(name = "S_USER")
@Data
@NamedQuery(name=User.FIND_ALL_USERS, query = "select u from User u")
@NamedQuery(name=User.FIND_USER_BY_ID, query = "select u from User u where u.Id = :id")
@NamedQuery(name=User.FIND_USER_RATING_BY_ID, query = "SELECT r from User u INNER JOIN u.userRatings r WHERE u.Id = :id")
@NamedQuery(name=User.FIND_USER_RESERVATIONS_BY_ID, query = "SELECT r from Reservation r WHERE r.id.clientId = :id")
@NamedQuery(name=User.FIND_USER_SERVICES_PROVIDED_BY_ID, query = "SELECT s from User u INNER JOIN u.serviceProvided s WHERE u.Id = :id")
@NamedQuery(name=User.FIND_ROLE_BY_USER_ID, query = "SELECT ur from UserRole ur INNER JOIN ur.role r WHERE ur.id.userId = :id")
public class User extends AbstractEntity {

    private static final long serialVersionUID = 1L;
	public static final String FIND_ALL_USERS = "user.findAllUsers";
    public static final String FIND_USER_BY_ID = "user.findUserById";
    public static final String FIND_USER_RATING_BY_ID = "user.findUserRatingByUserId";
    public static final String FIND_USER_RESERVATIONS_BY_ID = "user.findUserReservationsByUserId";
    public static final String FIND_USER_SERVICES_PROVIDED_BY_ID = "user.findUserServicesProvidedByUserId";
    public static final String FIND_ROLE_BY_USER_ID = "user.findRoleByUserId";

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
    @JsonbProperty(nillable=true)
    private byte[] picture;

    @OneToMany(mappedBy = "rater")
    @JsonbProperty(nillable=true)
    private Collection<UserRating> userRatings;

    @OneToMany(mappedBy = "artist")
    @JsonbProperty(nillable=true)
    private Collection<Reservation> reservations;

    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "SERVICE_ID")
    )
    @JsonbProperty(nillable=true)
    private Collection<ServiceProvided> serviceProvided;

    @OneToMany
    @JsonbProperty(nillable=true)
    private Collection<UserRole> userRoles;
}
