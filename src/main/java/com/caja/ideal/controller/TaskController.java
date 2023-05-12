package com.caja.ideal.controller;

import com.caja.ideal.mapper.TaskMapper;
import com.caja.ideal.models.TaskModels;
import com.caja.ideal.service.ITaskService;
import com.caja.ideal.utils.Responde;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    private ITaskService service;

    @GetMapping("/all")
    public ResponseEntity<List<TaskMapper>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskModels> get(@PathVariable @Min(1) Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping("/add")
    public ResponseEntity<TaskModels> add(@Valid @RequestBody TaskModels taskModels) {
        return new ResponseEntity<>(service.save(taskModels), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<TaskModels> update(@Valid @RequestBody TaskModels taskModels, @PathVariable @Min(1) Long id) {
        return ResponseEntity.ok(service.update(taskModels, id));
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Responde> delete(@PathVariable @Min(1) Long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
