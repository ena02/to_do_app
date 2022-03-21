package com.tasklist.backendspringboot.repo;

import com.tasklist.backendspringboot.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("SELECT p FROM Task p where " +
            "(:title IS NULL OR LOWER(p.title) LIKE LOWER(CONCAT('%', :title, '%') ) ) AND " +
            "(:completed IS NULL OR p.completed=:completed) AND " +
            "(:priorityId IS NULL OR p.priority.id=:priorityId) AND " +
            "(:categoryId IS NULL OR p.category.id=:categoryId)")
    Page<Task> findByParams(@Param("title") String title,
                            @Param("completed") Integer completed,
                            @Param("priorityId") Long priorityId,
                            @Param("categoryId") Long categoryId,
                            Pageable pageable);

}
