package com.IOT.MiniProject.controller;

import com.IOT.MiniProject.service.DeviceRepository;
import com.IOT.MiniProject.service.SensorRepository;
import com.IOT.MiniProject.table.request.ReqAddSensor;
import com.IOT.MiniProject.table.Sensor;
import com.IOT.MiniProject.table.request.ReqUpValSensor;
import com.IOT.MiniProject.table.request.ReqUpinfoSensor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sensor")
@CrossOrigin("*")
public class SensorController {
    @Autowired
    private SensorRepository sensorRepository;
    @Autowired
    private DeviceRepository deviceRepository;

    @PostMapping("/Add_Sensors")
    public APIResponse addSensors(@RequestBody ReqAddSensor reqSensor) {
        APIResponse response = new APIResponse();
        Sensor sensor = new Sensor();
        if(reqSensor.getName().isEmpty()){
            response.setStatus("Failed");
            response.setMessage("Sensor Name is Empty");
            response.setDataValue(null);

            return response;
        }else if (sensorRepository.findByName(reqSensor.getName()) != null){
            response.setStatus("Failed");
            response.setMessage("Sensor Name Already Exists");
            response.setDataValue(null);
            return response;
        }else if (reqSensor.getName().length() > 20){
            response.setStatus("Failed");
            response.setMessage("Sensor Name is too long");
            response.setDataValue(null);
            return response;
        }else if (reqSensor.getPin() < 0){
            response.setStatus("Failed");
            response.setMessage("Pin Number is Not Negative Value");
            response.setDataValue(null);
            return response;
        }
        else if (deviceRepository.findById(reqSensor.getDeviceId()).equals(Optional.empty())){
            response.setStatus("Failed");
            response.setMessage("Device ID is Invalid");
            response.setDataValue(null);
            return response;
        }
        else {
            //Generating Random Sensor UUID
            String id = java.util.UUID.randomUUID().toString();
            System.out.println(reqSensor.getDeviceId());
            System.out.println(deviceRepository.findById(reqSensor.getDeviceId()));

            sensor.setId(id);
            sensor.setName(reqSensor.getName());
            sensor.setPin(String.valueOf(reqSensor.getPin()));
            sensor.setDeviceId(reqSensor.getDeviceId());
            sensor.setValue(null);
            sensorRepository.save(sensor);

            response.setStatus("Success");
            response.setMessage("Sensors Added Successfully");
            response.setDataValue(sensorRepository.findByName(reqSensor.getName()));
        }
        return response;
    }

    @PostMapping("/Delete_Sensors")
    public APIResponse deleteSensor(@RequestBody String name) {
        APIResponse response = new APIResponse();
        if(name.isEmpty()){
            response.setStatus("Failed");
            response.setMessage("Sensor Name is Empty");
            response.setDataValue(null);
            return response;
        }else if (sensorRepository.findByName(name) == null ){
            response.setStatus("Failed");
            response.setMessage(" Sensor Name Not Exists");
            response.setDataValue(sensorRepository.findByName(name));
            return response;
        }else {
            String id = sensorRepository.findByName(name).getId();
            sensorRepository.deleteById(id);
            response.setStatus("Success");
            response.setMessage("Sensor Deleted Successfully");
            response.setDataValue(sensorRepository.findAll());
        }
        return response;
    }

    @GetMapping("/getSensorInfoByDeviceId/{deviceId}")
    public List<Sensor> getSensorInfoByDeviceId(@PathVariable String deviceId) {
        if(deviceId.isEmpty()){
            return null;
        }else if (deviceRepository.findById(deviceId).equals(Optional.empty())){
            return null;
        }
        else{
            return sensorRepository.findByDeviceId(deviceId);
        }
    }

    @PostMapping("/UpdateValueSensor")
    public APIResponse updateValueSensor(@RequestBody ReqUpValSensor reqUpSensor) {
        APIResponse response = new APIResponse();
        if(reqUpSensor.getName().isEmpty()){
            response.setStatus("Failed");
            response.setMessage("Sensor Name is Empty");
            response.setDataValue(null);
            return response;
        }else if (sensorRepository.findByName(reqUpSensor.getName()) == null ){
            response.setStatus("Failed");
            response.setMessage(" Sensor Name Not Exists");
            response.setDataValue(sensorRepository.findByName(reqUpSensor.getName()));
            return response;
        }else {
            Sensor sensor = sensorRepository.findByName(reqUpSensor.getName());
            sensor.setValue(reqUpSensor.getValue());
            sensorRepository.save(sensor);
            response.setStatus("Success");
            response.setMessage("Sensor Value Updated Successfully");
            response.setDataValue(sensorRepository.findByName(reqUpSensor.getName()));
        }
        return response;

    }

    @PostMapping("/UpdateInfoSensor")
    public APIResponse updateInfoSensor(@RequestBody ReqUpinfoSensor reqUpinfoSensor) {
        APIResponse response = new APIResponse();
        if(reqUpinfoSensor.getName().isEmpty()){
            response.setStatus("Failed");
            response.setMessage("Sensor Name is Empty");
            response.setDataValue(null);
            return response;
        }else if (sensorRepository.findByName(reqUpinfoSensor.getName()) == null ){
            response.setStatus("Failed");
            response.setMessage(" Sensor Name Not Exists");
            response.setDataValue(sensorRepository.findByName(reqUpinfoSensor.getName()));
            return response;
        }else if (reqUpinfoSensor.getNewName().length() > 20){
            response.setStatus("Failed");
            response.setMessage("Sensor New Name is too long");
            response.setDataValue(null);
            return response;
        }else if(sensorRepository.findByName(reqUpinfoSensor.getNewName()) != null) {
            response.setStatus("Failed");
            response.setMessage("Sensor New Name Already Exists");
            response.setDataValue(sensorRepository.findByName(reqUpinfoSensor.getNewName()));
            return response;
        }else {
            Sensor sensor = sensorRepository.findByName(reqUpinfoSensor.getName());
            sensor.setName(reqUpinfoSensor.getNewName());
            sensorRepository.save(sensor);
            response.setStatus("Success");
            response.setMessage("Sensor Updated Successfully");
            response.setDataValue(sensorRepository.findByName(reqUpinfoSensor.getNewName()));
        }
        return response;
    }

}
