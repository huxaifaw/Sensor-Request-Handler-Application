# Sensor-Request-Handler-Application

The goal is to develop a system that can handle multiple requests with high throughput.

Developed the API in Java Spring Boot.
Utilized Apache Kafka, Redis Cache and MongoDB.

- Kafka acts as a distributed streaming platform that can handle high-throughput data ingestion. Kafka’s ability to process high volumes of messages ensures that system can handle the multiple requests efficiently.
- Using Redis for distributed caching provides fast access to check if a request ID has already been processed, which helps in handling duplicate requests effectively. Also, Redis in-memory data store ensures low latency and high performance.
- MongoDB is horizontally scalable and handles large volumes of data well. MongoDB’s flexible schema is also beneficial for handling varied sensor data.
- Kafka can be scaled horizontally by adding more brokers and partitions, allowing the system to handle increased load.
- Spring Boot Application can be scaled horizontally by running multiple instances behind a load balancer and an API Gateway.
- Redis and MongoDB both can be scaled horizontally by sharding.
