package com.hsbc.fw.portal.scheduler.consumer;

import com.hsbc.fw.portal.scheduler.schedulerframework.ScheduledTask;
import org.springframework.stereotype.Component;

@Component("Task1")
public class ConsumerTask1 implements ScheduledTask {

    @Override
    public void run() {
        System.out.println("Executing Task 1 logic");
        // here think like this class will connect to some external system and fetch the data
        // and then process the data
        // and then save the data to the database
    }

}
