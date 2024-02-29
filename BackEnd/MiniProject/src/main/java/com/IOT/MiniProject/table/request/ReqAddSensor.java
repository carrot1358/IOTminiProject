package com.IOT.MiniProject.table.request;

import lombok.Data;

@Data
public class ReqAddSensor {
    private String name;
    private int pin;
    private String deviceId;
}
