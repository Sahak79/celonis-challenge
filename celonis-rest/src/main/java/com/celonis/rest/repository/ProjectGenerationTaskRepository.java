package com.celonis.rest.repository;

import com.celonis.rest.model.ProjectGenerationTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectGenerationTaskRepository extends JpaRepository<ProjectGenerationTask, Long> {
}
