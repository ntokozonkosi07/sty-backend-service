package com.railroad.entity.serviceProvided;

import com.railroad.entity.requirement.Requirement;

import java.util.Collection;

public class ServiceProvidedDto {
    private Long Id;
    private String name;
    private Collection<Requirement> requirements;
    private Collection<Long> artistsIds;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

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

    public Collection<Long> getArtistsIds() {
        return artistsIds;
    }

    public void setArtistsIds(Collection<Long> artistsIds) {
        this.artistsIds = artistsIds;
    }
}
