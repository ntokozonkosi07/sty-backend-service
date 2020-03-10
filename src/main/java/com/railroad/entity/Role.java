package com.railroad.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "S_ROLE")
@NamedQuery(name = Role.FIND_ALL_ROLES, query = "SELECT r FROM Role r")
@NamedQuery(name = Role.FIND_ALL_ROLE_BY_ID, query = "SELECT r from Role r WHERE r.Id = :id")
public class Role extends AbstractEntity {
    public static final String FIND_ALL_ROLES = "role.findAllRoles";
    public static final String FIND_ALL_ROLE_BY_ID = "role.findRoleById";

    @Getter @Setter
    private String name;
}
