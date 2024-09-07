package com.hsbc.fw.portal.scheduler.schedulerframework.controller;

import com.hsbc.fw.portal.scheduler.schedulerframework.DynamicTaskRegistrar;
import com.hsbc.fw.portal.scheduler.schedulerframework.config.SchedulerConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class JsonUploadController {

    private static final Logger logger = LoggerFactory.getLogger(JsonUploadController.class);

    private final Path root = Paths.get("uploads");

    @Autowired
    private SchedulerConfiguration schedulerConfig;

    @Autowired
    private DynamicTaskRegistrar taskRegistrar;

    @Autowired
    private ResourceLoader resourceLoader;

    // GET: Show the file upload page
    @GetMapping("/")
    public String showUploadPage() {
        return "upload";
    }

    // POST: Handle file upload
    @PostMapping("/uploadConfig")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, Model model) {
        try {
            // Save the uploaded file to the uploads directory
            if (!Files.exists(root)) {
                Files.createDirectory(root);
            }
            Path filePath = this.root.resolve(file.getOriginalFilename());
            Files.write(filePath, file.getBytes());

            // Reload the task configuration from the uploaded JSON file
            reloadTaskConfiguration(filePath);

            model.addAttribute("message", "Configuration uploaded and tasks reloaded successfully!");
        } catch (IOException e) {
            model.addAttribute("message", "Failed to upload and process the file.");
            logger.error("Error while processing the uploaded file", e);
        }

        return "upload";
    }

    // Load the JSON configuration and update tasks
    private void reloadTaskConfiguration(Path filePath) throws IOException {
        String jsonContent = new String(Files.readAllBytes(filePath));
        schedulerConfig.loadFromJson(jsonContent);  // Reload JSON into schedulerConfig

        // Re-register the tasks with the new configuration
        taskRegistrar.registerTasks();
    }
}
