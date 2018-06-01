package com.celonis.rest.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "task_type")
public class TaskType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    private String name;

    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TaskType)) return false;
        TaskType taskType = (TaskType) o;
        return Objects.equals(getId(), taskType.getId()) &&
                Objects.equals(getName(), taskType.getName()) &&
                Objects.equals(getDescription(), taskType.getDescription());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId(), getName(), getDescription());
    }
}
