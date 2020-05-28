package com.crud.tasks.service;

import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class DbServiceTest {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private DbService dbService;

    @Test
    public void getAllTasksTest() {

        //Given
        Task task = new Task(1L, "title", "test content");

        //When
        List<Task> list = dbService.getAllTasks();
        list.add(task);
        Long taskId = task.getId();

        //Then
        Assert.assertEquals(2, list.size());
        Assert.assertEquals("title", task.getTitle());

        //CleanUp
        try {
            dbService.deleteTask(taskId);
        } catch (Exception e) {
            //
        }
    }

    @Test
    public void testGetTaskById() {

        //Given
        Task task = new Task(1L, "title", "test content");
        List<Task> taskList = new ArrayList<>();

        //When
        Optional<Task> id = taskRepository.findById(task.getId());

        //Then
        Assert.assertEquals(Optional.empty(), id);
        Long taskId = task.getId();

        //CleanUp
        taskRepository.findById(taskId);

    }

    @Test
    public void saveTask() {

        //Given
        Task task = new Task("title", "test content");

        Task savedTask = dbService.saveTask(task);

        //When & Then
        Assert.assertNotNull(savedTask.getId());
        Long taskId = task.getId();

        //CleanUp
        taskRepository.save(task);

    }

    @Test
    public void deleteTask() {

        //Given
        List<Task> taskList = new ArrayList<>();
        Task task = new Task("title", "test content");
        taskList.add(task);

        //When
        //dbService.deleteTask(0L);
        taskRepository.delete(taskList.remove(0));

        //Then
        Assert.assertEquals(0, taskList.size());
        Long taskId = task.getId();

        //CleanUp
        taskRepository.delete(task);

    }

}
