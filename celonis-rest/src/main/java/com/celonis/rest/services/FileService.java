package com.celonis.rest.services;

import com.celonis.rest.exceptions.InternalException;
import com.celonis.rest.model.ProjectGenerationTask;
import com.celonis.rest.repository.ProjectGenerationTaskRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.URL;

@Service
public class FileService {

    private TaskService taskService;

    private ProjectGenerationTaskRepository projectGenerationTaskRepository;

    @Autowired
    public FileService(TaskService taskService,
                       ProjectGenerationTaskRepository projectGenerationTaskRepository) {
        this.projectGenerationTaskRepository = projectGenerationTaskRepository;
        this.taskService = taskService;
    }

    @PostConstruct
    public void init() {
        taskService.setFileService(this);
    }

    public ResponseEntity<FileSystemResource> getTaskResult(Long taskId) {
        ProjectGenerationTask projectGenerationTask = taskService.getTask(taskId);
        File inputFile = new File(projectGenerationTask.getStorageLocation());

        if (!inputFile.exists()) {
            throw new InternalException("File not generated yet");
        }

        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        respHeaders.setContentDispositionFormData("attachment", "challenge.zip");

        return new ResponseEntity<>(new FileSystemResource(inputFile), respHeaders, HttpStatus.OK);
    }

    void storeResult(Long taskId, URL url) throws IOException {
        ProjectGenerationTask projectGenerationTask = taskService.getTask(taskId);
        File outputFile = File.createTempFile(taskId.toString(), ".zip");
        outputFile.deleteOnExit();
        projectGenerationTask.setStorageLocation(outputFile.getAbsolutePath());
        projectGenerationTaskRepository.save(projectGenerationTask);
        try (InputStream is = url.openStream();
             OutputStream os = new FileOutputStream(outputFile)) {
            IOUtils.copy(is, os);
        }
    }

}
