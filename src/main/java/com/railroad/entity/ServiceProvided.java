package com.railroad.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;

@Entity
@Table(name = "S_SERVICE")
@Data
@NamedQuery(name = ServiceProvided.FIND_ALL_SERVICE_PROVIDED, query = "SELECT s FROM ServiceProvided s")
@NamedQuery(name = ServiceProvided.FIND_ALL_SERVICE_PROVIDED_REQUIREMENTS, query = "SELECT r FROM ServiceProvided s INNER JOIN s.requirements r WHERE s.Id = :id")
@NamedQuery(name = ServiceProvided.FIND_ALL_SERVICE_PROVIDED_BY_ARTIST_ID, query = "SELECT s FROM ServiceProvided s INNER JOIN s.artists a WHERE a.Id = :id")
@NamedQuery(name = ServiceProvided.FIND_SERVICE_PROVIDED_BY_ID, query = "SELECT s FROM ServiceProvided s WHERE s.Id = :id")
public class ServiceProvided extends AbstractEntity {

    public static final String FIND_ALL_SERVICE_PROVIDED = "FIND_ALL_SERVICE_PROVIDED";
    public static final String FIND_ALL_SERVICE_PROVIDED_REQUIREMENTS = "FIND_ALL_SERVICE_PROVIDED_REQUIREMENTS";
    public static final String FIND_ALL_SERVICE_PROVIDED_BY_ARTIST_ID = "FIND_ALL_SERVICE_PROVIDED_BY_ARTISTS";
    public static final String FIND_SERVICE_PROVIDED_BY_ID = "FIND_SERVICE_PROVIDED_BY_ID";
    public static final String FIND_SERVICES_PROVIDED_BY_REQUIREMENT_ID = "FIND_SERVICES_PROVIDED_BY_REQUIREMENT_ID";

    public ServiceProvided() {}

    @NotEmpty(message = "name cannot be empty")
    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "requirements")
    private Collection<ServiceProvidedRequirement> requirements;

    @ManyToMany(mappedBy = "servicesProvided")
    private Collection<User> artists;
}
