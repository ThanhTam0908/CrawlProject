package com.project.compareproduct.backend.model;

import lombok.*;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Entity
@Table(name = "roles", schema = "public")
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Getter
    @Setter
    @Id
    @SequenceGenerator(name = "roles_id_seq", sequenceName = "roles_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roles_id_seq")
    @Column(name = "id")
    private Long id;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    @NaturalId
    @Column(length = 60)
    private RoleName name;
}
