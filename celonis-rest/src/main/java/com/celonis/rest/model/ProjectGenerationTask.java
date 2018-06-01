package com.celonis.rest.model;

import com.celonis.rest.model.lcp.TaskStatus;
import com.celonis.rest.model.converter.TaskStatusConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "project_generation_task")
public class ProjectGenerationTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "from_value")
    private int fromValue;

    @Column(name = "to_value")
    private int toValue;

    @Transient
    private int currentValue;

    @Transient
    private boolean canceled;

    @Column(name = "creation_date")
    private Date creationDate;

    @Column(name = "storage_location")
    @JsonIgnore
    private String storageLocation;

    @Convert(converter = TaskStatusConverter.class)
    private TaskStatus status;

    @ManyToOne
    @JoinColumn(name="type_id")
    private TaskType taskType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public int getFromValue() {
        return fromValue;
    }

    public void setFromValue(int fromValue) {
        this.fromValue = fromValue;
    }

    public int getToValue() {
        return toValue;
    }

    public void setToValue(int toValue) {
        this.toValue = toValue;
    }

    public int getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(int currentValue) {
        this.currentValue = currentValue;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public String getStorageLocation() {
        return storageLocation;
    }

    public void setStorageLocation(String storageLocation) {
        this.storageLocation = storageLocation;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProjectGenerationTask)) return false;
        ProjectGenerationTask task = (ProjectGenerationTask) o;
        return getFromValue() == task.getFromValue() &&
                getToValue() == task.getToValue() &&
                Objects.equals(getId(), task.getId()) &&
                Objects.equals(getCreationDate(), task.getCreationDate()) &&
                Objects.equals(getStorageLocation(), task.getStorageLocation()) &&
                Objects.equals(getStatus(), task.getStatus()) &&
                Objects.equals(getTaskType(), task.getTaskType());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId(), getFromValue(), getToValue(), getCreationDate(), getStorageLocation(), getStatus(), getTaskType());
    }
}
