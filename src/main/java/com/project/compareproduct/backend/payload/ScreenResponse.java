package com.project.compareproduct.backend.payload;

import lombok.*;

@Getter
@Setter
@ToString
public class ScreenResponse {
    private double size;
    private String type;
    private long refreshRate;
    private String aspectRatio;
    private long ppi;
    private String resolution;
    private String hdrSupport;
    private String touchScreen;
}
