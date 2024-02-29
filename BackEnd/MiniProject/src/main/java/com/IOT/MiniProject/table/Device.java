package com.IOT.MiniProject.table;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document("device")
public class Device {
    private String id;
    private String name;
    private List<String> sensors;
    public Device() {
        this.sensors = new ArrayList<>();
    }
    public void setSensors(List<Sensor> sensorList) {
        // Extract sensor IDs and set them to the device
        List<String> sensorIds = new ArrayList<>();
        for (Sensor sensor : sensorList) {
            sensorIds.add(sensor.getId()); // Assuming Sensor class has an "id" field
        }
        this.sensors = sensorIds;
    }
}
