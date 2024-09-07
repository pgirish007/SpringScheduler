package com.hsbc.fw.portal.kafka.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {

    private static final String TOPIC = "scheduler-config";  // Kafka topic to send to

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void sendMessage(String key, Object message) {
        kafkaTemplate.send(TOPIC, key, message);
    }
}
