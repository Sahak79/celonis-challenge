package com.celonis.rest.model.lcp;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonDeserialize(using = TaskStatus.TaskStatusDeserializer.class)
public enum TaskStatus {
    NEW(1, "Task not Configured (1/4)"),
    EXECUTABLE(2, "Task not Configured (2/4)"),
    EXECUTING(3, "Executing ... (3/4)"),
    FINISHED(4, "Finished (4/4)");

    private int id;
    private String name;

    TaskStatus(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static TaskStatus fromId(int id) {
        for (TaskStatus status :TaskStatus.values()){
            if (status.getId()==id){
                return status;
            }
        }
        throw new UnsupportedOperationException(
                "The id " + id + " is not supported!");
    }

    @JsonCreator
    public static TaskStatus fromValue(final JsonNode jsonNode) {
        for (TaskStatus type : TaskStatus.values()) {
            if (type.id == jsonNode.get("id").asInt()) {
                return type;
            }
        }
        return null;
    }


    public static class TaskStatusDeserializer extends StdDeserializer<TaskStatus> {
        public TaskStatusDeserializer() {
            super(TaskStatus.class);
        }

        @Override
        public TaskStatus deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
            final JsonNode jsonNode = jp.readValueAsTree();
            Integer id = jsonNode.get("id").asInt();

            for (TaskStatus me: TaskStatus.values()) {
                if ( me.getId()==id) {
                    return me;
                }
            }
            throw dc.mappingException("Cannot deserialize TaskStatus from id " + id );
        }
    }

}

