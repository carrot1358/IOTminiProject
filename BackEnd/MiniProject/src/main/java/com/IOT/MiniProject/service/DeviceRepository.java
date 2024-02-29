package com.IOT.MiniProject.service;

import com.IOT.MiniProject.table.Device;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DeviceRepository extends MongoRepository<Device, String> {
    public Device findByName(String name);
}
