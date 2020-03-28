package com.railroad.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Entity
@Table(name = "S_SERVICE")
@Data
@NamedQuery(name = ServiceProvided.FIND_ALL_SERVICE_PROVIDED, query = "SELECT s FROM ServiceProvided s")
@NamedQuery(name = ServiceProvided.FIND_ALL_SERVICE_PROVIDED_REQUIREMENTS, query = "SELECT r FROM ServiceProvided s INNER JOIN s.requirements r WHERE s.Id = :id")
@NamedQuery(name = ServiceProvided.FIND_ALL_SERVICE_PROVIDED_ARTISTS, query = "SELECT a FROM ServiceProvided s INNER JOIN s.artists a WHERE s.Id = :id")
@NamedQuery(name = ServiceProvided.FIND_SERVICE_PROVIDED_BY_ID, query = "SELECT s FROM ServiceProvided s WHERE s.Id = :id")
public class ServiceProvided extends AbstractEntity {

    public static final String FIND_ALL_SERVICE_PROVIDED = "serviceProvided.getServicesProvided";
    public static final String FIND_ALL_SERVICE_PROVIDED_REQUIREMENTS = "serviceProvided.getServicesProvided.Requirements";
    public static final String FIND_ALL_SERVICE_PROVIDED_ARTISTS = "serviceProvided.getServicesProvided.artists";
    public static final String FIND_SERVICE_PROVIDED_BY_ID = "serviceProvided.getServiceProvidedById";

    public ServiceProvided() {}

    public ServiceProvided(
            @NotEmpty(message = "name cannot be empty") String name,
            Collection<Requirement> requirements,
            Collection<User> artists,
            @Min(value = 0,message = "price cannot be less than 0") double price
    ) {
        this.setName(name);
        this.setRequirements(requirements);
        this.setArtists(artists);
        this.setPrice(price);
    }

    public ServiceProvided(
            @NotNull Long id,
            @NotEmpty(message = "name cannot be empty") String name,
            Collection<Requirement> requirements,
            Collection<User> artists,
            @Min(value = 0,message = "price cannot be less than 0") double price
    ) {
        this(name,requirements,artists,price);
        this.setId(id);
    }

    @NotEmpty(message = "name cannot be empty")
    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany
    @JoinTable(
            name = "S_SERVICE_S_REQUIREMENT",
            joinColumns = @JoinColumn(name = "SERVICE_PROVIDED_ID"),
            inverseJoinColumns = @JoinColumn(name = "REQUIREMENT_ID")
    )
    private Collection<Requirement> requirements;

    @ManyToMany(mappedBy = "serviceProvided")
    private Collection<User> artists;

    @Min(value = 0, message = "price cannot be less than 0")
    private double price;
}
