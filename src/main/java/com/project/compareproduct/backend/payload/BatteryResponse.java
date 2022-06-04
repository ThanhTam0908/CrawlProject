package com.project.compareproduct.backend.payload;

import lombok.*;

@Getter
@Setter
@ToString
public class BatteryResponse {
    private double capacity;
    private long fullChargeTime;
    private String type;
    private String replaceable;
    private String fastCharge;
    private String usbPowerDelivery;
    private long chargePower;
}
