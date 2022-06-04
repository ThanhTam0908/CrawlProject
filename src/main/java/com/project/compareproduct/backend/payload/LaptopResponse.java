package com.project.compareproduct.backend.payload;

import com.project.compareproduct.backend.model.*;

import lombok.*;

@Getter
@Setter
@ToString
public class LaptopResponse {
    private String name;
    private double weight;
    private double area;
    private String cpu;
    private String graphic;
    private String screen;
    private long ram;
    private long storageCapacity;
    private String storageType;
    private double price;
    private String os;
    private String advantage;
    private double length;
    private double width;
    private double thick;
    private String url;
}
