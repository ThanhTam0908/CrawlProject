package com.project.compareproduct.backend.payload;

import lombok.*;

@Getter
@Setter
@ToString
public class RamResponse {
    private long size;
    private String channels;
    private long clock;
    private String type;
    private String upgradable;
}
