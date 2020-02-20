package com.railroad.entity;

import com.railroad.entity.serviceProvided.ServiceProvided;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Collection;

@Entity
@Table(name = "S_REQUIREMENT")
public class Requirement extends AbstractEntity {
    @NotEmpty(message = "name cannot be empty")
    private String name;

    @Size(min = 1, max = 280)
    @NotEmpty(message = "description cannot be empty")
    private String description;

    @NotEmpty(message = "price cannot be empty")
    private double price;

    @ManyToMany(mappedBy = "requirements")
    private Collection<ServiceProvided> services;

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

    public Collection<ServiceProvided> getServices() {
        return services;
    }

    public void setServices(Collection<ServiceProvided> services) {
        this.services = services;
    }
}
