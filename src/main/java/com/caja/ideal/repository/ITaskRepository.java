package com.caja.ideal.repository;

import com.caja.ideal.models.TaskModels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITaskRepository extends JpaRepository<TaskModels, Long> {
}
