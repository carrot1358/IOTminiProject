package com.IOT.MiniProject.service;

import com.IOT.MiniProject.table.Sensor;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SensorRepository extends MongoRepository<Sensor, String> {
    public Sensor findByName(String name);
    public List<Sensor> findByPin(String pin);
    public List<Sensor> findByDeviceId(String deviceId);

}
