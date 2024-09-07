package com.hsbc.fw.portal.scheduler.schedulerframework.controller;

import com.hsbc.fw.portal.scheduler.schedulerframework.config.SchedulerConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/scheduler")
public class SchedulerController {

    @Autowired
    private SchedulerConfiguration schedulerConfiguration;

    @PostMapping("/upload-config")
    public String uploadTaskConfig(@RequestParam("file") MultipartFile file) {
        try {
            // Save the uploaded file to the tasks-config.json
            File configFile = new File("tasks-config.json");
            file.transferTo(configFile);

            // Reload the configuration
            schedulerConfiguration.loadConfigFromJson();

            return "Configuration updated successfully!";
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to update configuration!";
        }
    }
}
