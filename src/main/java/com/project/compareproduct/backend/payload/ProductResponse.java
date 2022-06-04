package com.project.compareproduct.backend.payload;

import lombok.*;

@Getter
@Setter
@ToString
public class ProductResponse {
    private String name;
    private String image;
    private double weight;
    private double area;
    private double screentobodyRatio;
    private String colors;
    private long maxRamSlot;
    private long storageSlot;
}
