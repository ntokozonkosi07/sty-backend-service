package com.railroad.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "S_ROLE")
@NamedQuery(name = Role.FIND_ALL_ROLES, query = "SELECT r FROM Role r JOIN FETCH r.userRoles ur")
@NamedQuery(name = Role.FIND_ROLE_BY_ID, query = "SELECT r from Role r WHERE r.Id = :id")
@NamedQuery(name = Role.FIND_ROLES_BY_USER_ID, query = "SELECT r from Role r INNER JOIN r.userRoles u WHERE u.id.userId = :id")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Role extends AbstractEntity {
    public static final String FIND_ALL_ROLES = "role.findAllRoles";
    public static final String FIND_ROLE_BY_ID = "role.findRoleById";
    public static final String FIND_ROLES_BY_USER_ID = "FIND_ROLES_BY_USER_ID";

    @Column(unique = true)
    @NonNull
    private String name;

    @OneToMany(mappedBy = "role")
    private Collection<UserRole> userRoles;
}
