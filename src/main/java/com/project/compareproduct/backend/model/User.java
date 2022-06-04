package com.project.compareproduct.backend.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users", schema = "public", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "email" })
})
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User extends DateAudit {
    @Getter
    private static final long serialVersionUID = 5644797795094164062L;

    @Getter
    @Setter
    @Id
    @SequenceGenerator(name = "users_id_seq", sequenceName = "users_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_seq")
    @Column(name = "id")
    private Long id;

    @Getter
    @Setter
    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Getter
    @Setter
    @NotBlank
    private String name;

    @Getter
    @Setter
    @NotBlank
    @Size(max = 40)
    @Email
    private String email;

    @Getter
    @Setter
    @NotBlank
    @Size(max = 100)
    private String password;

    @Getter
    @Setter
    @Column(name = "email_token")
    private String emailToken;

    @Getter
    @Setter
    @Column(name = "password_reset_token")
    private String passwordResetToken;

    @Getter
    @Setter
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @Getter
    @Setter
    @Column(name = "gender")
    private String gender;

    @Getter
    @Setter
    @Column(name = "image")
    private String image;

    @Getter
    @Setter
    @Column(name = "address")
    private String address;

    @Getter
    @Setter
    @Column(name = "cell_phone")
    private String cellPhone;

    public User(User user) {
        this.id = user.id;
        this.email = user.email;
        this.password = user.password;
        this.roles = user.roles;
        this.setCreatedAt(user.getCreatedAt());
        this.setUpdatedAt(user.getUpdatedAt());
    }
}
