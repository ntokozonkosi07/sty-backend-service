package com.railroad.entity.serviceProvided;

import com.railroad.entity.AbstractEntity;
import com.railroad.entity.Artist;
import com.railroad.entity.requirement.Requirement;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;

@Entity
@Table(name = "S_SERVICE")
@NamedQuery(name = ServiceProvided.FIND_ALL_SERVICE_PROVIDED, query = "SELECT s FROM ServiceProvided s")
@NamedQuery(name = ServiceProvided.FIND_ALL_SERVICE_PROVIDED_REQUIREMENTS, query = "SELECT r FROM ServiceProvided s INNER JOIN s.requirements r WHERE s.Id = :id")
@NamedQuery(name = ServiceProvided.FIND_ALL_SERVICE_PROVIDED_ARTISTS, query = "SELECT a FROM ServiceProvided s INNER JOIN s.artists a WHERE s.Id = :id")
public class ServiceProvided extends AbstractEntity {
    public static final String FIND_ALL_SERVICE_PROVIDED = "serviceProvided.getServicesProvided";
    public static final String FIND_ALL_SERVICE_PROVIDED_REQUIREMENTS = "serviceProvided.getServicesProvided.Requirements";
    public static final String FIND_ALL_SERVICE_PROVIDED_ARTISTS = "serviceProvided.getServicesProvided.artists";

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
    private Collection<Artist> artists;

    @Min(value = 0, message = "price cannot be less than 0")
    private double price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Requirement> getRequirements() {
        return requirements;
    }

    public void setRequirements(Collection<Requirement> requirements) {
        this.requirements = requirements;
    }

    public Collection<Artist> getArtists() {
        return artists;
    }

    public void setArtists(Collection<Artist> artists) {
        this.artists = artists;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
