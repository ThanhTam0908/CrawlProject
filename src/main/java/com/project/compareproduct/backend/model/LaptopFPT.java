package com.project.compareproduct.backend.model;

import com.project.compareproduct.backend.payload.LaptopResponse;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "laptopFPT")
public class LaptopFPT {
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "weight")
    private double weight;

    @Column(name = "length")
    private double length;

    @Column(name = "width")
    private double width;

    @Column(name = "thick")
    private double thick;

    @Column(name = "area")
    private double area;

    @Column(name = "cpu")
    private String cpu;

    @Column(name = "graphic")
    private String graphic;

    @Column(name = "screen")
    private String screen;

    @Column(name = "advantage")
    private String advantage;

    @Column(name = "ram")
    private long ram;

    @Column(name = "storage_capacity")
    private long storageCapacity;

    @Column(name = "storage_type")
    private String storageType;

    @Column(name = "price")
    private double price;

    @Column(name = "os")
    private String os;

    @Column(name = "url")
    private String url;

    public LaptopFPT(LaptopResponse laptop) {
        this.thick = laptop.getThick();
        this.length = laptop.getLength();
        this.width = laptop.getWidth();
        this.area = laptop.getArea();
        this.cpu = laptop.getCpu();
        this.graphic = laptop.getGraphic();
        this.advantage = laptop.getAdvantage();
        this.name = laptop.getName();
        this.os = laptop.getOs();
        this.price = laptop.getPrice();
        this.ram = laptop.getRam();
        this.screen = laptop.getScreen();
        this.storageCapacity = laptop.getStorageCapacity();
        this.storageType = laptop.getStorageType();
        this.weight = laptop.getWeight();
        this.url = laptop.getUrl();
    }
}
