package com.hsbc.fw.portal.scheduler.consumer;

import com.hsbc.fw.portal.scheduler.schedulerframework.ScheduledTask;
import org.springframework.stereotype.Component;

@Component("HTTPTask")
public class HTTPTask implements ScheduledTask {

    @Override
    public void run() {
        System.out.println("Executing Task 2 logic");
        // this task call to say Unicorn API and get the data
        // and then process the data
    }

    @Override
    public void executeCommand(String command) {
        System.out.println("Executing Task 2 logic with command: " + command);
    }
}
