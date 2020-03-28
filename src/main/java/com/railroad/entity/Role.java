package com.railroad.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "S_ROLE")
@NamedQuery(name = Role.FIND_ALL_ROLES, query = "SELECT r FROM Role r")
@NamedQuery(name = Role.FIND_ROLE_BY_ID, query = "SELECT r from Role r WHERE r.Id = :id")
@NoArgsConstructor
@RequiredArgsConstructor
public class Role extends AbstractEntity {
    public static final String FIND_ALL_ROLES = "role.findAllRoles";
    public static final String FIND_ROLE_BY_ID = "role.findRoleById";

    @Getter @Setter
    @Column(unique = true, nullable = false)
    @NonNull
    private String name;
}
