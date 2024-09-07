package com.hsbc.fw.portal.scheduler.schedulerframework;

import com.hsbc.fw.portal.scheduler.schedulerframework.config.SchedulerConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaMessageListener {

    @Autowired
    private SchedulerConfiguration schedulerConfig;

    @Autowired
    private DynamicTaskRegistrar taskRegistrar;

    @KafkaListener(topics = "scheduler-config", groupId = "scheduler-consumer-group")
    public void listen(String jsonMessage) {
        // Handle JSON message and reload tasks
        schedulerConfig.loadFromJson(jsonMessage);
        taskRegistrar.registerTasks();
    }
}

