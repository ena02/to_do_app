package com.tasklist.backendspringboot.controller;

import com.tasklist.backendspringboot.entity.Priority;
import com.tasklist.backendspringboot.repo.PriorityRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/priority")
public class PriorityController {

    private PriorityRepository priorityRepository;

    public PriorityController(PriorityRepository priorityRepository) {
        this.priorityRepository = priorityRepository;
    }

    @GetMapping("/test")
    public List<Priority> test() {

        List<Priority> list = priorityRepository.findAll();
        System.out.println(list);

        return list;
    }

    @PostMapping("/add")
    public Priority add(@RequestBody Priority priority) {
        return priorityRepository.save(priority);
    }
}
