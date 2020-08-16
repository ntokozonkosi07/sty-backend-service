package com.railroad.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name = "REF_COLOUR")
@NamedQuery(name = Colour.FIND_ALL_COLOURS, query = "SELECT c FROM Colour c")
public class Colour extends AbstractEntity {
    public static final String FIND_ALL_COLOURS = "Colour.FIND_ALL_COLOURS";
    @Size(max = 36, min=2)
    @NotEmpty()
    private String name;

    @NotEmpty()
    @Size(max = 7, min=3)
    private String hexcode;

}
