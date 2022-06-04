package com.project.compareproduct.backend.payload;

import lombok.*;

@Getter
@Setter
@ToString
public class StorageResponse {
    private long size;
    private String bus;
    private String channels;
    private String type;
    private String NVMe;
}
