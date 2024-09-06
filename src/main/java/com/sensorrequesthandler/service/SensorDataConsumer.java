package com.sensorrequesthandler.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sensorrequesthandler.domain.SensorData;
import com.sensorrequesthandler.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class SensorDataConsumer {

    @Autowired
    private SensorRepository sensorRepository;

    @KafkaListener(topics = "sensor-data-topic", groupId = "sensor-group")
    public void consume(String sensorDataJson) throws JsonProcessingException {
        SensorData sensorData = new ObjectMapper().readValue(sensorDataJson, SensorData.class);
        sensorRepository.save(sensorData);
    }
}
