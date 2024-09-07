package com.hsbc.fw.portal.kafka.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/kafka")
public class ProducerController {

    @Autowired
    private ProducerService producerService;

    @PostMapping("/publish")
    public ResponseEntity<String> publishConfig(@RequestBody String jsonConfig) {
        producerService.sendMessage("configKey", jsonConfig);
        return ResponseEntity.ok("Message sent to Kafka successfully");
    }
}
