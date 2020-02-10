package com.railroad.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;

@Entity
@Table(name = "S_SERVICE")
public class ServiceUtility extends AbstractEntity {
    @NotEmpty(message = "name cannot be empty")
    private String name;

    @ManyToMany
    @JoinTable(
            name = "S_SERVICE_S_REQUIREMENT",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "RATING_ID")
    )
    private Collection<Requirement> requirements;

    @ManyToMany(mappedBy = "serviceUtilities")
    private Collection<Artist> artists;

    private double price;

    @OneToOne(mappedBy = "serviceUtility")
    private Reservation reservation;
}
