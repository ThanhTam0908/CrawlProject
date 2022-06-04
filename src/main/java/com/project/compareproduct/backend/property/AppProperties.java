package com.project.compareproduct.backend.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "compare")
public class AppProperties {
    @Getter
    @Setter
    private String applicationUrl = "http://localhost:3000";

    @Getter
    @Setter
    private Admin admin = new Admin();

    @Getter
    @Setter
    private Jwt jwt;

    @Getter
    @Setter
    private String uploadDir;

    public static class Admin {
        @Getter
        @Setter
        private String email;

        @Getter
        @Setter
        private String password;

        @Getter
        @Setter
        private String name;
    }

    public static class Jwt {
        @Getter
        @Setter
        private String secret;

        @Getter
        @Setter
        private long expirationMillis = 864000000L; // 10 days

        @Getter
        @Setter
        private int shortLivedMillis = 120000; // Two minutes
    }
}
