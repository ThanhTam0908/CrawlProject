package com.project.compareproduct.backend.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.*;

@Entity
@Table(name = "cpu")

public class CPU {
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
    @Column(name = "base_frequency")
    private double baseFrequency;

    @Getter
    @Setter
    @Column(name = "turbo_frequency")
    private double turboFrequency;

    @Getter
    @Setter
    @Column(name = "cores")
    private long cores;

    @Getter
    @Setter
    @Column(name = "threads")
    private long threads;

    @Getter
    @Setter
    @Column(name = "l3_cache")
    private long l3Cache;

    @Getter
    @Setter
    @Column(name = "integrated_gpu")
    private String integratedGpu;

    @Getter
    @Setter
    @Column(name = "fabrication_process")
    private String fabricationProcess;

    public CPU(long id, String name, long baseFrequency, long turboFrequency,
            long cores, long threads, long l3Cache, String integratedGPU,
            String fabricationProcess) {
        this.id = id;
        this.name = name;
        this.baseFrequency = baseFrequency;
        this.turboFrequency = turboFrequency;
        this.cores = cores;
        this.threads = threads;
        this.l3Cache = l3Cache;
        this.integratedGpu = integratedGPU;
        this.fabricationProcess = fabricationProcess;
    }

    public CPU() {

    }

}
