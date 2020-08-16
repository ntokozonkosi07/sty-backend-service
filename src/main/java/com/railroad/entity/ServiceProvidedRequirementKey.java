package com.railroad.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class ServiceProvidedRequirementKey implements Serializable {

    @Column(name = "service_id")
    Long serviceProvidedId;

    @Column(name = "requirement_id")
    Long requirementId;
}
