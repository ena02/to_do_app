package com.tasklist.backendspringboot.controller;

import com.tasklist.backendspringboot.entity.Stat;
import com.tasklist.backendspringboot.service.StatService;
import com.tasklist.backendspringboot.util.MyLogger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatController {

    private final StatService statService;

    public StatController(StatService statService) {
        this.statService = statService;
    }

    private final Long defaultId = 1l;

    @GetMapping("/stat")
    public ResponseEntity<Stat> findById() {

        MyLogger.showMethodName("StatController: findById() ---------------------------------------------------------- ");

        return ResponseEntity.ok(statService.findById(defaultId));
    }
}
