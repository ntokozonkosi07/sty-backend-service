package com.railroad.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class UserRoleKey implements Serializable {
    @Getter @Setter @Column(name = "user_id")
    private Long userId;

    @Getter @Setter @Column(name = "role_id")
    private Long roleId;
}
