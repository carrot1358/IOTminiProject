package com.IOT.MiniProject.controller;

import com.IOT.MiniProject.service.DeviceRepository;
import com.IOT.MiniProject.service.SensorRepository;
import com.IOT.MiniProject.table.Device;
import com.IOT.MiniProject.table.Sensor;
import com.IOT.MiniProject.table.request.ReqAddDevice;
import com.IOT.MiniProject.table.request.ReqUpInfoDevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/device")
@CrossOrigin("*")
public class DeviceController {
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private SensorRepository sensorRepository;

    @PostMapping("/Add_Devices")
    public APIResponse addDevice(@RequestBody ReqAddDevice reqDevice) {
        APIResponse response = new APIResponse();
        Device device = new Device();
        if(reqDevice.getName().isEmpty()){
            response.setStatus("Failed");
            response.setMessage("Device Name is Empty");
            response.setDataValue(null);
            return response;
        }else if (deviceRepository.findByName(reqDevice.getName()) != null ){
            response.setStatus("Failed");
            response.setMessage(" Device Name Already Exists");
            response.setDataValue(deviceRepository.findByName(reqDevice.getName()));
            return response;
        }else if (reqDevice.getName().length() > 20){
            response.setStatus("Failed");
            response.setMessage("Device Name is too long");
            response.setDataValue(null);
            return response;
        }else {
            String id = java.util.UUID.randomUUID().toString();
            device.setId(id);
            device.setName(reqDevice.getName());

            deviceRepository.save(device);

            response.setStatus("Success");
            response.setMessage("Device Added Successfully");
            response.setDataValue(deviceRepository.findByName(reqDevice.getName()));
        }

        return response;
    }

    @PostMapping("/Delete_Devices")
    public APIResponse deleteDevice(@RequestBody String name) {
        APIResponse response = new APIResponse();
        if(name.isEmpty()){
            response.setStatus("Failed");
            response.setMessage("Device Name is Empty");
            response.setDataValue(null);
            return response;
        }else if (deviceRepository.findByName(name) == null ){
            response.setStatus("Failed");
            response.setMessage(" Device Name Not Exists");
            response.setDataValue(deviceRepository.findByName(name));
            return response;
        }else {
            String id = deviceRepository.findByName(name).getId();
            deviceRepository.deleteById(id);
            List<Sensor> sensors = sensorRepository.findByDeviceId(id);
            for (Sensor sensor : sensors) {
                sensorRepository.deleteById(sensor.getId());
            }
            response.setStatus("Success");
            response.setMessage("Device Deleted Successfully");
            response.setDataValue(deviceRepository.findAll());
        }
        return response;
    }

    @PostMapping("/UpdateInfo_Devices")
    public APIResponse updateDevice(@RequestBody ReqUpInfoDevice reqUpInfoDevice) {
        APIResponse response = new APIResponse();
        if(reqUpInfoDevice.getName().isEmpty()){
            response.setStatus("Failed");
            response.setMessage("Device Name is Empty");
            response.setDataValue(null);
            return response;
        }else if (deviceRepository.findByName(reqUpInfoDevice.getName()) == null ){
            response.setStatus("Failed");
            response.setMessage(" Device Name Not Exists");
            response.setDataValue(deviceRepository.findByName(reqUpInfoDevice.getName()));
            return response;
        }else if (reqUpInfoDevice.getNewName().length() > 20){
            response.setStatus("Failed");
            response.setMessage("Device New Name is too long");
            response.setDataValue(null);
            return response;
        }else if(deviceRepository.findByName(reqUpInfoDevice.getNewName()) != null) {
            response.setStatus("Failed");
            response.setMessage("Device New Name Already Exists");
            response.setDataValue(deviceRepository.findByName(reqUpInfoDevice.getNewName()));
            return response;
        }else {
            Device device = deviceRepository.findByName(reqUpInfoDevice.getName());
            device.setName(reqUpInfoDevice.getNewName());
            deviceRepository.save(device);
            response.setStatus("Success");
            response.setMessage("Device Updated Successfully");
            response.setDataValue(deviceRepository.findAll());
        }
        return response;
    }

    @PostMapping("/getDeviceIDByname")
    public String getDevice(@RequestBody String name) {
        return deviceRepository.findByName(name).getId();
    }

    @GetMapping("/getAllDevices")
    public List<Device> getAllDevices() {
        return deviceRepository.findAll();
    }



}
