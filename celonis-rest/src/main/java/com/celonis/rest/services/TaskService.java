package com.celonis.rest.services;

import com.celonis.rest.exceptions.InternalException;
import com.celonis.rest.exceptions.NotFoundException;
import com.celonis.rest.model.ProjectGenerationTask;
import com.celonis.rest.model.TaskType;
import com.celonis.rest.model.lcp.TaskStatus;
import com.celonis.rest.repository.ProjectGenerationTaskRepository;
import com.celonis.rest.repository.TaskTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.*;

@Service
public class TaskService {

    private Timer timer = new Timer();

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskService.class);

    private ProjectGenerationTaskRepository projectGenerationTaskRepository;

    private TaskTypeRepository taskTypeRepository;

    private FileService fileService;

    @Autowired
    public TaskService(ProjectGenerationTaskRepository projectGenerationTaskRepository,
                       TaskTypeRepository taskTypeRepository) {
        this.projectGenerationTaskRepository = projectGenerationTaskRepository;
        this.taskTypeRepository = taskTypeRepository;
    }

    public List<TaskType> listTypes() {
        return taskTypeRepository.findAll();
    }

    public List<ProjectGenerationTask> listTasks() {
        return projectGenerationTaskRepository.findAll();
    }

    public ProjectGenerationTask createTask(ProjectGenerationTask projectGenerationTask) {
        projectGenerationTask.setCreationDate(new Date());
        return projectGenerationTaskRepository.save(projectGenerationTask);
    }

    public ProjectGenerationTask getTask(Long taskId) {
        return get(taskId);
    }


    public void executeCountingTask(ProjectGenerationTask task){
        if(task!= null){
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    if(task.isCanceled()){
                        task.setCurrentValue(0);
                        task.setStatus(TaskStatus.EXECUTABLE);
                        update(task.getId(), task);
                        this.cancel();
                    }
                    task.setCurrentValue(task.getCurrentValue()+1);
                    if(task.getCurrentValue() > task.getToValue()){
                        task.setStatus(TaskStatus.FINISHED);
                        update(task.getId(), task);
                        this.cancel();
                    }
                }
            };
            timer.schedule(timerTask, 0, 1000);
        }
    }

    public ProjectGenerationTask update(Long taskId, ProjectGenerationTask projectGenerationTask) {
        ProjectGenerationTask existing = get(taskId);
        existing.setStatus(projectGenerationTask.getStatus());
        if(projectGenerationTask.getFromValue()>=projectGenerationTask.getToValue()){
            throw new InternalException("From value bigger then to value.");
        }
        existing.setFromValue(projectGenerationTask.getFromValue());
        existing.setToValue(projectGenerationTask.getToValue());
        return projectGenerationTaskRepository.save(existing);
    }

    public void delete(Long taskId) {
        projectGenerationTaskRepository.delete(taskId);
    }

    public void executeTask(Long taskId) {
        URL url = Thread.currentThread().getContextClassLoader().getResource("challenge.zip");
        if (url == null) {
            throw new InternalException("Zip file not found");
        }
        try {
            fileService.storeResult(taskId, url);
        } catch (Exception e) {
            throw new InternalException(e);
        }
    }

    private ProjectGenerationTask get(Long taskId) {
        ProjectGenerationTask projectGenerationTask = projectGenerationTaskRepository.findOne(taskId);
        if (projectGenerationTask == null) {
            throw new NotFoundException();
        }
        return projectGenerationTask;
    }

    void setFileService(FileService fileService) {
        this.fileService = fileService;
    }
}
