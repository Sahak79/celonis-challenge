package com.celonis.rest.controller;

import com.celonis.rest.model.ProjectGenerationTask;
import com.celonis.rest.model.TaskType;
import com.celonis.rest.model.lcp.TaskStatus;
import com.celonis.rest.services.FileService;
import com.celonis.rest.services.TaskService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/springjwt")
public class TaskController {

    private final TaskService taskService;

    private final FileService fileService;

    private Map<Long, ProjectGenerationTask> taskMap = new ConcurrentHashMap<>();

    public TaskController(TaskService taskService, FileService fileService) {
        this.taskService = taskService;
        this.fileService = fileService;
    }

    @GetMapping("/types")
    @PreAuthorize("hasAuthority('STANDARD_USER')")
    public List<TaskType> listTypes() {
        return taskService.listTypes();
    }

    @GetMapping("/tasks")
    @PreAuthorize("hasAuthority('STANDARD_USER')")
    public List<ProjectGenerationTask> listTasks() {
        return taskService.listTasks();
    }

    @PostMapping("/tasks")
    @PreAuthorize("hasAuthority('STANDARD_USER')")
    public ProjectGenerationTask createTask(@RequestBody @Valid ProjectGenerationTask projectGenerationTask) {
        return taskService.createTask(projectGenerationTask);
    }

    @GetMapping("/tasks/{taskId}")
    @PreAuthorize("hasAuthority('STANDARD_USER')")
    public ProjectGenerationTask getTask(@PathVariable Long taskId) {
        ProjectGenerationTask task = taskMap.get(taskId);
        if(task==null){
            task = taskService.getTask(taskId);
            if (task.getStatus().equals(TaskStatus.EXECUTING)){
                taskMap.put(taskId, task);
                task.setCurrentValue(task.getFromValue());
                taskService.executeCountingTask(task);
            }
        }
        task.setCanceled(false);
        return task;
    }

    @PutMapping("/tasks/{taskId}")
    @PreAuthorize("hasAuthority('STANDARD_USER')")
    public ProjectGenerationTask updateTask(@PathVariable Long  taskId,
                                            @RequestBody @Valid ProjectGenerationTask projectGenerationTask) {
        return taskService.update(taskId, projectGenerationTask);
    }

    @DeleteMapping("/tasks/{taskId}")
    @PreAuthorize("hasAuthority('STANDARD_USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable Long taskId) {
        taskService.delete(taskId);
    }

    @PostMapping("/tasks/{taskId}/execute")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('STANDARD_USER')")
    public void executeTask(@PathVariable Long taskId) {
        taskService.executeTask(taskId);
    }

    @GetMapping("/tasks/{taskId}/cancel")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('STANDARD_USER')")
    public ProjectGenerationTask cancelExecution(@PathVariable Long taskId) {
        ProjectGenerationTask task = taskMap.get(taskId);
        task.setCanceled(true);
        return taskMap.remove(taskId);
    }

    @GetMapping("/tasks/{taskId}/result")
    @PreAuthorize("hasAuthority('STANDARD_USER')")
    public ResponseEntity<FileSystemResource> getResult(@PathVariable Long taskId) {
        return fileService.getTaskResult(taskId);
    }

}
