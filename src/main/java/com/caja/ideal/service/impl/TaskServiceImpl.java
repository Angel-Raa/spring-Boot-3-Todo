package com.caja.ideal.service.impl;

import com.caja.ideal.exception.ResourceNotFound;
import com.caja.ideal.mapper.TaskMapper;
import com.caja.ideal.models.TaskModels;
import com.caja.ideal.repository.ITaskRepository;
import com.caja.ideal.service.ITaskService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements ITaskService {
    @Autowired
    private ITaskRepository repository;

    @ResponseStatus(HttpStatus.OK)
    @Transactional(readOnly = true)
    @Override
    public List<TaskMapper> findAll() {
        return repository.findAll().stream()
                .map(task -> new TaskMapper(task.getTitle(), task.getDescription(), task.getFinished(), task.getCreatedAt()))
                .collect(Collectors.toList());
    }

    @ResponseStatus(HttpStatus.OK)
    @Transactional(readOnly = true)
    @Override
    public TaskModels findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Not Fount Task"));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    @Override
    public TaskModels save(TaskModels task) {
        TaskModels taskModels = repository.save(task);
        return taskModels;
    }

    @ResponseStatus(HttpStatus.OK)
    @Transactional
    @Override
    public TaskModels update(TaskModels task, Long id) {
        TaskModels taskToModels = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Not Fount Task"));
        BeanUtils.copyProperties(task, taskToModels, "id");
        TaskModels updatedTask = repository.save(taskToModels);
        return updatedTask;
    }

    @ResponseStatus(HttpStatus.OK)
    @Transactional
    @Override
    public ResponseEntity<Object> delete(Long id) {
        Optional<TaskModels> taskModels = repository.findById(id);
        HashMap<String, Object> response = new HashMap<>();
        if (taskModels.isPresent()) {
            response.put("message", "Task deleted");
            repository.deleteById(id);
            return new ResponseEntity<>(response, HttpStatus.OK);

        }
        throw new ResourceNotFound("Not Fount Task");
    }
}
