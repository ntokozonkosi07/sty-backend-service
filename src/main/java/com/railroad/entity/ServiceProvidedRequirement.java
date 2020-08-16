package com.railroad.entity;

import lombok.Data;

import javax.annotation.PostConstruct;
import javax.persistence.*;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.Collection;

@Data
@Entity
@Table(name = "S_SERVICE_S_REQUIREMENT")
public class ServiceProvidedRequirement implements Serializable {
    @EmbeddedId
    ServiceProvidedRequirementKey id;

    @ManyToOne
    @MapsId("service_id")
    @JoinColumn(name = "service_id")
    ServiceProvided services;

    @ManyToOne
    @MapsId("requirement_id")
    @JoinColumn(name = "requirement_id")
    Requirement requirements;

    @Column(name = "price")
    @Min(value = 0)
    double basePrice;

    @PostConstruct
    private void init(){
        this.basePrice = 0;
    }
}
