package com.railroad.entity;

//import sun.text.resources.cldr.en.FormatData_en_TT;

import lombok.Getter;
import lombok.Setter;

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
@NamedQuery(name= Requirement.FIND_REQUIREMENTS_BY_SERVICE_PROVIDED, query = "SELECT r FROM Requirement r INNER JOIN r.servicesProvided s WHERE s.Id = :id")
public class Requirement extends AbstractEntity {
    public static final String FIND_ALL_REQUIREMENTS = " FIND_ALL_REQUIREMENTS";
    public static final String FIND_REQUIREMENT_BY_ID = "FIND_REQUIREMENT_BY_ID";
    public static final String FIND_REQUIREMENTS_BY_SERVICE_PROVIDED = "FIND_REQUIREMENTS_BY_SERVICE_PROVIDED";

    @Getter @Setter
    @NotEmpty(message = "name cannot be empty")
    @Column(nullable = false, unique = true)
    private String name;

    @Getter @Setter
    @Size(min = 1, max = 280)
    @NotNull(message = "description cannot be empty")
    @Column(nullable = false)
    private String description;

    @Getter @Setter
    @NotNull(message = "price cannot be empty")
    @Column(nullable = false)
    private Double price;

    @Getter @Setter
    @JsonbProperty(nillable = true)
    @ManyToMany(mappedBy = "requirements", fetch = FetchType.LAZY)
    private Collection<ServiceProvided> servicesProvided;

}
