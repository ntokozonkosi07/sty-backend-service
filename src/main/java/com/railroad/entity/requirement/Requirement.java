package com.railroad.entity.requirement;

import com.railroad.entity.AbstractEntity;
import com.railroad.entity.User;
import com.railroad.entity.serviceProvided.ServiceProvided;
import sun.text.resources.cldr.en.FormatData_en_TT;

import javax.json.bind.annotation.JsonbProperty;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;

@Entity
@Table(name = "S_REQUIREMENT")
@NamedQuery(name= Requirement.FIND_ALL_REQUIREMENTS, query = "select r from Requirement r")
@NamedQuery(name= Requirement.FIND_REQUIREMENT_BY_ID, query = "select r from Requirement r where r.Id = :id")
public class Requirement extends AbstractEntity {
    public static final String FIND_ALL_REQUIREMENTS = "requirement.findAllRequirements";
    public static final String FIND_REQUIREMENT_BY_ID = "requirement.findRequirementById";

    @NotEmpty(message = "name cannot be empty")
    @Column(nullable = false, unique = true)
    private String name;

    @Size(min = 1, max = 280)
    @NotNull(message = "description cannot be empty")
    @Column(nullable = false)
    private String description;

    @NotNull(message = "price cannot be empty")
    @Column(nullable = false)
    private Double price;

    @JsonbProperty(nillable = true)
    @ManyToMany(mappedBy = "requirements", fetch = FetchType.EAGER)
    private Collection<ServiceProvided> servicesProvided;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Collection<ServiceProvided> getServicesProvided() {
        return servicesProvided;
    }

    public void setServicesProvided(Collection<ServiceProvided> services) {
        this.servicesProvided = services;
    }
}
