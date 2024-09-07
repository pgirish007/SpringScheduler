package com.hsbc.fw.portal.scheduler.schedulerframework.config;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Component
public class SchedulerConfiguration {
    Logger logger= org.slf4j.LoggerFactory.getLogger(SchedulerConfiguration.class);
    private ArrayList<ScheduledTask> tasks;

    @PostConstruct
    public void loadConfigFromJson() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            SchedulerConfiguration config = mapper.readValue(new File("tasks-config.json"), SchedulerConfiguration.class);
            this.tasks = config.getTasks();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load configuration from a JSON string
    public void loadFromJson(String jsonContent) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Parse JSON and map it to the tasks list
            SchedulerConfiguration config = objectMapper.readValue(jsonContent, SchedulerConfiguration.class);
            this.tasks = config.getTasks();
            logger.info("Configuration reloaded successfully.");
        } catch (IOException e) {
            logger.error("Failed to load configuration from JSON", e);
        }
    }

    public ArrayList<ScheduledTask> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<ScheduledTask> tasks) {
        this.tasks = tasks;
    }

    public static class ScheduledTask {
        private String name;
        private String cron;
        private long fixedRate;
        private long fixedDelay;
        private boolean enabled;
        private String className;
        private LinkedList<String> commands;
        private String startDateTime;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCron() {
            return cron;
        }

        public void setCron(String cron) {
            this.cron = cron;
        }

        public long getFixedRate() {
            return fixedRate;
        }

        public void setFixedRate(long fixedRate) {
            this.fixedRate = fixedRate;
        }

        public long getFixedDelay() {
            return fixedDelay;
        }

        public void setFixedDelay(long fixedDelay) {
            this.fixedDelay = fixedDelay;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public LinkedList<String> getCommands() {
            return commands;
        }

        public void setCommands(LinkedList<String> commands) {
            this.commands = commands;
        }

        public String getStartDateTime() {
            return startDateTime;
        }

        public void setStartDateTime(String startDateTime) {
            this.startDateTime = startDateTime;
        }


        // Getters and Setters for all properties
    }
}