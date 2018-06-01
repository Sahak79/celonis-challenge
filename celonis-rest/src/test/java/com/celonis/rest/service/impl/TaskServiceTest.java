package com.celonis.rest.service.impl;

import com.celonis.config.BasicTestConfig;
import com.celonis.rest.model.ProjectGenerationTask;
import com.celonis.rest.model.TaskType;
import com.celonis.rest.model.lcp.TaskStatus;
import com.celonis.rest.services.TaskService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TaskServiceTest extends BasicTestConfig {

    private static final int FROM_VALUE = 0;
    private static final int TO_VALUE = 10;
    private static final Integer TYPE_ID = 2;
    private static final String TYPE_NAME = "Count from X to Y";
    private static final String TYPE_DESCRIPTION = "This task counts from given X to given Y.";
    private static final Integer STATUS_ID = 1;

    @Autowired
    private TaskService taskService;

    @Test
    public void addBankAccount() {

        ProjectGenerationTask task = new ProjectGenerationTask();
        task.setFromValue(FROM_VALUE);
        task.setToValue(TO_VALUE);

        TaskType taskType = new TaskType();
        taskType.setId(TYPE_ID);
        taskType.setName(TYPE_NAME);
        taskType.setDescription(TYPE_DESCRIPTION);

        task.setTaskType(taskType);
        task.setStatus(TaskStatus.fromId(STATUS_ID));

        ProjectGenerationTask savedProjectGenerationTask = taskService.createTask(task);

        assertNotNull(savedProjectGenerationTask);
        assertEquals(FROM_VALUE, savedProjectGenerationTask.getFromValue());
        assertEquals(TO_VALUE, savedProjectGenerationTask.getToValue());
        assertEquals(TYPE_ID, savedProjectGenerationTask.getTaskType().getId());
        assertEquals(TaskStatus.fromId(STATUS_ID), savedProjectGenerationTask.getStatus());

    }
}