package com.project.compareproduct.backend.payload;

import lombok.*;

@Getter
@Setter
@ToString
public class GraphicResponse {
    private String name;
    private long tgp;
    private String type;
    private long fabricationProcess;
    private long gpuBaseClock;
    private long gpuBootClock;
    private double flops;
    private String memorySize;
    private String memoryType;
    private long memoryBus;
    private double memorySpeed;
    private long directxSupport;
}
