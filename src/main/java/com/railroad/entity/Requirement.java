package com.railroad.entity;

//import sun.text.resources.cldr.en.FormatData_en_TT;

import lombok.Data;

import javax.json.bind.annotation.JsonbProperty;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;

@Data
@Entity
@Table(name = "S_REQUIREMENT")
@NamedQuery(name= Requirement.FIND_ALL_REQUIREMENTS, query = "select r from Requirement r")
@NamedQuery(name= Requirement.FIND_REQUIREMENT_BY_ID, query = "select r from Requirement r where r.Id = :id")public class Requirement extends AbstractEntity {
    public static final String FIND_ALL_REQUIREMENTS = " FIND_ALL_REQUIREMENTS";
    public static final String FIND_REQUIREMENT_BY_ID = "FIND_REQUIREMENT_BY_ID";
    public static final String FIND_REQUIREMENTS_BY_SERVICE_PROVIDED = "FIND_REQUIREMENTS_BY_SERVICE_PROVIDED";

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
    @OneToMany(mappedBy = "requirements")
    private Collection<ServiceProvidedRequirement> requirements;

}
