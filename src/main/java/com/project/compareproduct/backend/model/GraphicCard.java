package com.project.compareproduct.backend.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "graphic_card")
public class GraphicCard {
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Getter
    @Setter
    @Column(name = "name")
    private String name;

    @Getter
    @Setter
    @Column(name = "tgp")
    private long tgp;

    @Getter
    @Setter
    @Column(name = "type")
    private String type;

    @Getter
    @Setter
    @Column(name = "fabrication_process")
    private long fabricationProcess;

    @Getter
    @Setter
    @Column(name = "gpu_base_clock")
    private long gpuBaseClock;

    @Getter
    @Setter
    @Column(name = "gpu_boot_clock")
    private long gpuBootClock;

    @Getter
    @Setter
    @Column(name = "flops")
    private double flops;

    @Getter
    @Setter
    @Column(name = "memory_size")
    private String memorySize;

    @Getter
    @Setter
    @Column(name = "memory_type")
    private String memoryType;

    @Getter
    @Setter
    @Column(name = "memory_bus")
    private long memoryBus;

    @Getter
    @Setter
    @Column(name = "memory_speed")
    private double memorySpeed;

    @Getter
    @Setter
    @Column(name = "directx_support")
    private long directxSupport;

}