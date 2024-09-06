package com.sensorrequesthandler.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sensorrequesthandler.domain.SensorData;
import com.sensorrequesthandler.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sensors")
public class SensorController {

    @Autowired
    private SensorService sensorService;

    @PostMapping
    public ResponseEntity<String> receiveSensorData(@RequestBody SensorData sensorData) throws JsonProcessingException {

        if (sensorService.isDuplicateRequest(sensorData.getRequestId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Duplicate Sensor Data");
        }
        sensorService.sendDataToKafka(sensorData);
        return ResponseEntity.ok("Sensor Data Successfully Received and Published!!!");
    }

    @GetMapping("/{id}")
    public ResponseEntity<SensorData> getSensorData(@PathVariable String id) {
        SensorData sensorData = sensorService.getSensorData(id);
        return ResponseEntity.ok(sensorData);
    }
}
