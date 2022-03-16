package com.tasklist.backendspringboot.controller;

import com.tasklist.backendspringboot.entity.Stat;
import com.tasklist.backendspringboot.repo.StatRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatController {

    StatRepository statRepository;

    public StatController(StatRepository statRepository) {
        this.statRepository = statRepository;
    }

    private final Long defaultId = 1l;

    @GetMapping("/stat")
    public ResponseEntity<Stat> findById() {


        return ResponseEntity.ok(statRepository.findById(defaultId).get());
    }
}
