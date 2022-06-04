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
@Table(name = "screen")
public class Screen {
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Getter
    @Setter
    @Column(name = "size")
    private double size;

    @Getter
    @Setter
    @Column(name = "type")
    private String type;

    @Getter
    @Setter
    @Column(name = "refresh_rate")
    private long refreshRate;

    @Getter
    @Setter
    @Column(name = "aspect_ratio")
    private String aspectRatio;

    @Getter
    @Setter
    @Column(name = "ppi")
    private long ppi;

    @Getter
    @Setter
    @Column(name = "resolution")
    private String resolution;

    @Getter
    @Setter
    @Column(name = "hdr_support")
    private String hdrSupport;

    @Getter
    @Setter
    @Column(name = "touch_screen")
    private String touchScreen;
}