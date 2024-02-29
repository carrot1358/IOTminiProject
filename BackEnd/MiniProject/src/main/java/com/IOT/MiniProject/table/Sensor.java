package com.IOT.MiniProject.table;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("sensor")
public class Sensor {
    private String id;
    private String name;
    private String pin;
    private String value;
    private String deviceId;
}
