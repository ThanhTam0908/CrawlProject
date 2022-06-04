package com.project.compareproduct.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("roleConfiguration")
public class RoleConfig {
    @Value("${spring.authorization.admin.role}")
    private List<String> adminRoles;
    @Value("${spring.authorization.user.role}")
    private List<String> userRoles;

    public List<String> getAdminRoles() {
        return adminRoles;
    }

    public List<String> getUserRoles() {
        return userRoles;
    }
}
