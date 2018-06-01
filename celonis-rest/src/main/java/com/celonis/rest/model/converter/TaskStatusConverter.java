package com.celonis.rest.model.converter;

import com.celonis.rest.model.lcp.TaskStatus;
import javax.persistence.AttributeConverter;

public class TaskStatusConverter implements AttributeConverter<TaskStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TaskStatus attribute) {
        return attribute.getId();
    }

    @Override
    public TaskStatus convertToEntityAttribute(Integer dbData) {
        return TaskStatus.fromId(dbData);
    }
}
