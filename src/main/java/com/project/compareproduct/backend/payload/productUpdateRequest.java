package com.project.compareproduct.backend.payload;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
public class productUpdateRequest {
    @Getter
    private long cpu_id;

    @Getter
    private long storage_id;

    @Getter
    private long graphic_id;

    @Getter
    private long ram_id;

    @Getter
    private long battery_id;

    @Getter
    private long screen_id;

    @Getter
    private String name;
    
    @Getter
    private double weight;

    @Getter
    private double area;

    @Getter
    private double screentobodyRatio;

    @Getter
    private String colors;

    @Getter
    private long maxRamSlot;

    @Getter
    private long storageSlot;
}
