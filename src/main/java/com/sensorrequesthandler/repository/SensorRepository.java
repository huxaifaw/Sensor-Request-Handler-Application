package com.sensorrequesthandler.repository;

import com.sensorrequesthandler.domain.SensorData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorRepository extends MongoRepository<SensorData, String> {
}
