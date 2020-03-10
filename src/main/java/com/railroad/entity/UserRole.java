package com.railroad.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "S_USER_ROLE")
public class UserRole {
    @EmbeddedId
    private UserRoleKey id;

    @Getter @Setter
    @ManyToOne()
    @MapsId("roleId")
    private Role role;

    @Getter @Setter
    @ManyToOne()
    @MapsId("userId")
    private User user;

    // permissions object will be here...
}
