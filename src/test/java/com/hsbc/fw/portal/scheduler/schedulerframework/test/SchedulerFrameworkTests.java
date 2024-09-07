package com.hsbc.fw.portal.scheduler.schedulerframework.test;

import com.hsbc.fw.portal.scheduler.schedulerframework.ScheduledTask;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SchedulerFrameworkTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void contextLoads() {
        assertThat(applicationContext).isNotNull();
    }

    @Test
    public void testScheduledTaskBeans() {
        Map<String, ScheduledTask> tasks = applicationContext.getBeansOfType(ScheduledTask.class);
        assertThat(tasks).isNotEmpty();
        assertThat(tasks).containsKeys("Task1", "Task2");
    }
}
