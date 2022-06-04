package com.project.compareproduct.backend.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ram")
public class Ram {
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Getter
    @Setter
    @Column(name = "size")
    private long size;

    @Getter
    @Setter
    @Column(name = "channels")
    private String channels;

    @Getter
    @Setter
    @Column(name = "clock")
    private long clock;

    @Getter
    @Setter
    @Column(name = "type")
    private String type;

    @Getter
    @Setter
    @Column(name = "upgradable")
    private String upgradable;


}
