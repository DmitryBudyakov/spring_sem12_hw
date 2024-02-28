package com.example.spring_sem12_hw.controller;

import com.example.spring_sem12_hw.model.Task;
import com.example.spring_sem12_hw.model.TaskStatus;
import com.example.spring_sem12_hw.repository.TaskRepository;
import com.example.spring_sem12_hw.service.FileWriterGateway;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
@AllArgsConstructor
public class TaskController {

    private final TaskRepository taskRepository;
    private final FileWriterGateway fileWriterGateway;
    private final String TASKS_FILENAME = "tasks.txt";

    /**
     * Просмотр списка всех задач
     *
     * @return
     */
    @GetMapping
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    /**
     * Создание задачи со статусом NOT_STARTED и текущим временем
     *
     * @param task
     * @return
     */
    @PostMapping
    public Task addTask(@RequestBody Task task) {
        task.setStatus(TaskStatus.NOT_STARTED);
        task.setDateCreate(LocalDateTime.now());
        // запись в файл
        String createTaskMsg = "TASK CREATE : ";
        fileWriterGateway.writeToFile(
                TASKS_FILENAME, createTaskMsg + task.toString());
        return taskRepository.save(task);
    }

    /**
     * Поиск всех задач по определенному статусу
     *
     * @param status
     * @return
     */
    @GetMapping("/status/{status}")
    public List<Task> getTasksByStatus(@PathVariable TaskStatus status) {
        return taskRepository.findByStatus(status);
    }

    /**
     * Обновление статуса задачи по её id
     *
     * @param id
     * @param updatedTask
     * @return
     */
    @PutMapping("/{id}")
    public Task updateTaskStatus(@PathVariable Long id, @RequestBody Task updatedTask) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            task.setStatus(updatedTask.getStatus());
            // запись в файл
            String updTaskStatus = "TASK STATUS UPD : ";
            fileWriterGateway.writeToFile(
                    TASKS_FILENAME, updTaskStatus + task.toString());
            return taskRepository.save(task);
        } else {
            return null;
        }
    }

    /**
     * Удаление задачи по id
     *
     * @param id
     */
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            //запись в файл
            String delTaskMsg = "TASK DELETE : ";
            fileWriterGateway.writeToFile(
                    TASKS_FILENAME, delTaskMsg + task.toString());
            taskRepository.deleteById(id);
        } else {
            return;
        }
//        taskRepository.deleteById(id);
    }


}
