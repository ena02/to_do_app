package com.tasklist.backendspringboot.controller;

import com.tasklist.backendspringboot.entity.Category;
import com.tasklist.backendspringboot.repo.CategoryRepository;
import com.tasklist.backendspringboot.search.CategorySearchValues;
import com.tasklist.backendspringboot.util.MyLogger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/all")
    public List<Category> findAll() {

        MyLogger.showMethodName("CategoryController: findAll() ---------------------------------------------------------- ");
        return categoryRepository.findAllByOrderByTitleAsc();
    }


    @PostMapping("/add")
    public ResponseEntity<Category> add(@RequestBody Category category) {

        MyLogger.showMethodName("CategoryController: add() ---------------------------------------------------------- ");


        if (category.getId() != null && category.getId() != 0) {
            return new ResponseEntity("redundant param: id MUST be null", HttpStatus.NOT_ACCEPTABLE);
        }

        if (category.getTitle() == null || category.getTitle().trim().length() == 0) {
            return new ResponseEntity("missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(categoryRepository.save(category));
    }

    @PutMapping("/update")
    public ResponseEntity<Category> update(@RequestBody Category category) {

        MyLogger.showMethodName("CategoryController: update() ---------------------------------------------------------- ");


        if (category.getId() == null && category.getId() == 0) {
            return new ResponseEntity("id can not be null", HttpStatus.NOT_ACCEPTABLE);
        }


        if (category.getTitle() == null || category.getTitle().trim().length() == 0) {
            return new ResponseEntity("missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(categoryRepository.save(category));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Category> findById(@PathVariable Long id) {

        MyLogger.showMethodName("CategoryController: findById() ---------------------------------------------------------- ");


        Category category = null;

        try {
            category = categoryRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return new ResponseEntity(String.format("id=%d not found", id), HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(category);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {

        MyLogger.showMethodName("CategoryController: delete() ---------------------------------------------------------- ");


        try {
            categoryRepository.deleteById(id);
            return new ResponseEntity("Success", HttpStatus.ACCEPTED);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity(String.format("Category with id = %d not found", id), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping("/search")
    public ResponseEntity<List<Category>> search(@RequestBody CategorySearchValues categorySearchValues) {

        MyLogger.showMethodName("CategoryController: search() ---------------------------------------------------------- ");

        return ResponseEntity.ok(categoryRepository.findByTitle(categorySearchValues.getText()));
    }
}
