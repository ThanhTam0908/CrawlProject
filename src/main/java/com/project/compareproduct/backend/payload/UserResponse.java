package com.project.compareproduct.backend.payload;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.project.compareproduct.backend.model.Role;
import com.project.compareproduct.backend.model.RoleName;
import com.project.compareproduct.backend.model.User;

@ToString
public class UserResponse {
    @Getter
    @Setter
    private Long id;
    @Getter
    @Setter
    private String email;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private Set<Role> roles = new HashSet<>();
    @Getter
    @Setter
    private boolean unverified = false;
    @Getter
    @Setter
    private boolean blocked = false;
    @Getter
    @Setter
    private boolean admin = false;
    @Getter
    @Setter
    private boolean goodUser = false;
    @Getter
    @Setter
    private boolean goodAdmin = false;
    @Getter
    @Setter
    private String token = "";

    public UserResponse(User user) {

        id = user.getId();
        name = user.getName();
        email = user.getEmail();
        roles = user.getRoles();
        List<RoleName> rolesName = roles.stream().map(role -> role.getName()).collect(Collectors.toList());
        admin = rolesName.contains(RoleName.ADMIN);
        goodUser = !(unverified || blocked);
        goodAdmin = goodUser && admin;
        token = token;
    }
}
