package com.tasklist.backendspringboot.controller;

import com.tasklist.backendspringboot.entity.Task;
import com.tasklist.backendspringboot.repo.TaskRepository;
import com.tasklist.backendspringboot.util.MyLogger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/task")
public class TaskController {

    private TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping("/all")
    public List<Task> findAll() {

        MyLogger.showMethodName("TaskController: findAll() ---------------------------------------------------------- ");

        return taskRepository.findAll();
    }

    @PostMapping("/add")
    public ResponseEntity<Task> add(@RequestBody Task Task) {

        MyLogger.showMethodName("TaskController: add() ---------------------------------------------------------- ");


        if (Task.getId() != null && Task.getId() != 0) {
            return new ResponseEntity("redundant param: id MUST be null", HttpStatus.NOT_ACCEPTABLE);
        }

        if (Task.getTitle() == null || Task.getTitle().trim().length() == 0) {
            return new ResponseEntity("missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }


        return ResponseEntity.ok(taskRepository.save(Task));
    }

    @PutMapping("/update")
    public ResponseEntity<Task> update(@RequestBody Task Task) {

        MyLogger.showMethodName("TaskController: update() ---------------------------------------------------------- ");


        if (Task.getId() == null && Task.getId() == 0) {
            return new ResponseEntity("id can not be null", HttpStatus.NOT_ACCEPTABLE);
        }

        if (Task.getTitle() == null || Task.getTitle().trim().length() == 0) {
            return new ResponseEntity("missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }

        taskRepository.save(Task);

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Task> findById(@PathVariable Long id) {

        MyLogger.showMethodName("TaskController: findById() ---------------------------------------------------------- ");


        Task task = null;

        try {
            task = taskRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return new ResponseEntity(String.format("id=%d not found", id), HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(task);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {

        MyLogger.showMethodName("TaskController: delete() ---------------------------------------------------------- ");


        try {
            taskRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity(String.format("Task with id = %d not found", id), HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity(HttpStatus.OK);
    }


}
