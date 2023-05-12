package com.caja.ideal.service;

import com.caja.ideal.mapper.TaskMapper;
import com.caja.ideal.models.TaskModels;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ITaskService {
    List<TaskMapper> findAll();
    TaskModels findById(Long id);
    TaskModels save(TaskModels task);
    TaskModels update(TaskModels task, Long id);

    ResponseEntity<Object> delete(Long id);

}
