package com.tasklist.backendspringboot.controller;

import com.tasklist.backendspringboot.entity.Task;
import com.tasklist.backendspringboot.repo.TaskRepository;
import com.tasklist.backendspringboot.search.TaskSearchValues;
import com.tasklist.backendspringboot.util.MyLogger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    @PostMapping("/search")
    public ResponseEntity<Page<Task>> search(@RequestBody TaskSearchValues taskSearchValues) {

        MyLogger.showMethodName("CategoryController: search() ---------------------------------------------------------- ");

        String text = taskSearchValues.getTitle() != null ? taskSearchValues.getTitle() : null;

        Integer completed = taskSearchValues.getCompleted() != null ? taskSearchValues.getCompleted() : null;

        Long priorityId = taskSearchValues.getPriorityId() != null ? taskSearchValues.getPriorityId() : null;
        Long categoryId = taskSearchValues.getCategoryId() != null ? taskSearchValues.getCategoryId() : null;

        Integer pageNumber = taskSearchValues.getPageNumber() != null ? taskSearchValues.getPageNumber() : null;
        Integer pageSize = taskSearchValues.getPageSize() != null ? taskSearchValues.getPageSize() : null;

        String sortColumn = taskSearchValues.getSortColumn() != null ? taskSearchValues.getSortColumn() : null;
        String sortDirection = taskSearchValues.getSortDirection() != null ? taskSearchValues.getSortDirection() : null;

        Sort.Direction direction = sortDirection == null || sortDirection.trim().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;


        Sort sort = Sort.by(direction, sortColumn);

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);

        Page result = taskRepository.findByParams(text, completed, priorityId, categoryId, pageRequest);
        /*List result = taskRepository.findByParams(taskSearchValues.getTitle(), taskSearchValues.getCompleted(), taskSearchValues.getPriorityId(), taskSearchValues.getCategoryId());*/

        return ResponseEntity.ok(result);
    }

}
