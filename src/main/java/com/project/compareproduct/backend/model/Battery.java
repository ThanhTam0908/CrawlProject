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
@Table(name = "battery")
public class Battery {
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private long id;

    @Getter
    @Setter
    @Column(name = "capacity")
    private double capacity;

    @Getter
    @Setter
    @Column(name = "full_charge_time")
    private long fullChargeTime;

    @Getter
    @Setter
    @Column(name = "type")
    private String type;

    @Getter
    @Setter
    @Column(name = "replaceable")
    private String replaceable;

    @Getter
    @Setter
    @Column(name = "fast_charge")
    private String fastCharge;

    @Getter
    @Setter
    @Column(name = "usb_power_delivery")
    private String usbPowerDelivery;

    @Getter
    @Setter
    @Column(name = "charge_power")
    private long chargePower;

}
