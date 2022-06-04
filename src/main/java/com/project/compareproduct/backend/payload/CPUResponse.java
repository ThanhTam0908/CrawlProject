package com.project.compareproduct.backend.payload;

import lombok.*;

@Getter
@Setter
@ToString
public class CPUResponse {
    private String name;
    private double baseFrequency;
    private double turboFrequency;
    private long cores;
    private long threads;
    private long l3cache;
    private String integratedGpu;
    private String fabricationProcess;
}
