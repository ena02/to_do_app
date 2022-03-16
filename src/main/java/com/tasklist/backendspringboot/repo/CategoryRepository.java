package com.tasklist.backendspringboot.repo;

import com.tasklist.backendspringboot.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c FROM Category c WHERE " +
            "(:title IS NULL OR :title='' OR LOWER(c.title) LIKE LOWER(CONCAT('%', :title, '%')) )" +
            "ORDER BY c.title ASC")
    List<Category> findByTitle(@Param("title") String title);

    List<Category> findAllByOrderByTitleAsc();
}
