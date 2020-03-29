package com.railroad.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Data
@Entity
@Table(name = "S_USER_ROLE")
@NamedQuery(name = UserRole.FIND_USER_ROLES_BY_USER_ID, query = "SELECT ur FROM UserRole ur WHERE ur.id.userId = :id")
public class UserRole {
    public static final String FIND_USER_ROLES_BY_USER_ID = "FIND_USER_ROLES_BY_USER_ID";

    @EmbeddedId
    private UserRoleKey id;

    @ManyToOne()
    @MapsId("roleId")
    private Role role;

    @ManyToOne()
    @MapsId("userId")
    private User user;
}
