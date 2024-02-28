package com.example.spring_sem12_hw.repository;

import com.example.spring_sem12_hw.model.Task;
import com.example.spring_sem12_hw.model.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    /**
     * Поиск задач по статусу
     * @param status Используемые статусы: ON_STARTED, IN_PROGRESS, COMPLETED
     * @return
     */
    List<Task> findByStatus(TaskStatus status);
}
