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
//@NamedQuery(name= Requirement.FIND_SERVICES_PROVIDED_BY_REQUIREMENT_ID, query = "SELECT s FROM Requirement r INNER JOIN r.servicesProvided s WHERE r.Id = :id")
public class ServiceProvided extends AbstractEntity {
    public static final String FIND_ALL_SERVICE_PROVIDED = "serviceProvided.getServicesProvided";

    @NotEmpty(message = "name cannot be empty")
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
