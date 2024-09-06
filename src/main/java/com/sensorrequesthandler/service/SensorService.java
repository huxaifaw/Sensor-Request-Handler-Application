package com.sensorrequesthandler.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sensorrequesthandler.domain.SensorData;
import com.sensorrequesthandler.exception.ResourceNotFoundException;
import com.sensorrequesthandler.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class SensorService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private SensorRepository sensorRepository;

    private static final long CACHE_EXPIRATION_SECONDS = 3600;

    public boolean isDuplicateRequest(String requestId) {
        Boolean exists = redisTemplate.hasKey(requestId);
        if (Boolean.TRUE.equals(exists)) {
            return true;
        }
        redisTemplate.opsForValue().set(requestId, "processed", CACHE_EXPIRATION_SECONDS, TimeUnit.SECONDS);
        return false;
    }

    public void sendDataToKafka(SensorData sensorData) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        kafkaTemplate.send("sensor-data-topic", sensorData.getRequestId(), mapper.writeValueAsString(sensorData));
    }

    public SensorData getSensorData(String id) {
        return sensorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Sensor Data doesn't exist for requestId: " + id));
    }
}