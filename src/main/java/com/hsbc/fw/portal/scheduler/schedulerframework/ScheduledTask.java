package com.hsbc.fw.portal.scheduler.schedulerframework;

public interface ScheduledTask extends Runnable {
    void executeCommand(String command);  // Define a method to handle task execution with command
}

