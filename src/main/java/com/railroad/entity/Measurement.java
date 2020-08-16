package com.railroad.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "REF_MEASUREMENT")
@NamedQuery(name=Measurement.FIND_ALL_MEASUREMENTS, query = "select u from Measurement u")
public class Measurement extends AbstractEntity{
    public static final String FIND_ALL_MEASUREMENTS = "Measurement.FIND_ALL_MEASUREMENTSS";

    private String unit;
    
    private String abbrevatedUnit;
}
