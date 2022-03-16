package com.tasklist.backendspringboot.repo;

import com.tasklist.backendspringboot.entity.Category;
import com.tasklist.backendspringboot.entity.Priority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriorityRepository extends JpaRepository<Priority, Long> {

    @Query("SELECT p FROM Priority p WHERE " +
            "(:title IS NULL OR :title='' OR LOWER(p.title) LIKE LOWER(CONCAT('%', :title, '%')) )" +
            "ORDER BY p.title ASC")
    List<Priority> findByTitle(@Param("title") String title);

    List<Priority> findAllByOrderByTitleAsc();
}
