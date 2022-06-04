package com.project.compareproduct.backend.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product {
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private long id;

    @Getter
    @Setter
    @Column(name = "name")
    private String name;

    @Getter
    @Setter
    @Column(name = "image")
    private String image;

    @Getter
    @Setter
    @Column(name = "weight")
    private double weight;

    @Getter
    @Setter
    @Column(name = "area")
    private double area;

    @Getter
    @Setter
    @Column(name = "screen_to_body_ratio")
    private double screentobodyRatio;

    @Getter
    @Setter
    @Column(name = "colors")
    private String colors;

    @Getter
    @Setter
    @ManyToOne(targetEntity = CPU.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "cpu_id", referencedColumnName = "id")
    private CPU cpu;

    @Getter
    @Setter
    @ManyToOne(targetEntity = GraphicCard.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "graphic_id", referencedColumnName = "id")
    private GraphicCard graphic;

    @Getter
    @Setter
    @ManyToOne(targetEntity = Battery.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "battery_id", referencedColumnName = "id")
    private Battery battery;

    @Getter
    @Setter
    @ManyToOne(targetEntity = Screen.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "screen_id", referencedColumnName = "id")
    private Screen screen;

    @Getter
    @Setter
    @ManyToOne(targetEntity = Ram.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "ram_id", referencedColumnName = "id")
    private Ram ram;

    @Getter
    @Setter
    @Column(name = "max_ram_slot")
    private long maxRamSlot;

    @Getter
    @Setter
    @ManyToOne(targetEntity = Storage.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "storage_id", referencedColumnName = "id")
    private Storage storage;

    @Getter
    @Setter
    @Column(name = "storage_slot")
    private long storageSlot;

}
