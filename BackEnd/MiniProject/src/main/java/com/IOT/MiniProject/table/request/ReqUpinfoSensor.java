package com.IOT.MiniProject.table.request;

import lombok.Data;

@Data
public class ReqUpinfoSensor {
    private String name;
    private String newName;
    private int pin;
    private String deviceId;
}
