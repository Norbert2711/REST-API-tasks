package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DbService service;

    @MockBean
    private TaskMapper taskMapper;

    @MockBean
    private TaskRepository taskRepository;

    @Test
    public void getAllTask() throws Exception {

        //Given
        List<Task> taskList = new ArrayList<>();
        taskList.add(new Task("1", "Test title", "Test content"));

        when(service.getAllTasks()).thenReturn(taskList);
        System.out.println(taskList.size());

        //When & Then
        mockMvc.perform(get("/v1/task/getTasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("Test title")));
    }

    @Test
    public void deleteTask() throws Exception {

        //Given
        Task task = new Task(1L, "Test title", "Test content");

        when(service.getTask(1L)).thenReturn(Optional.of(task));

        //When & Then
        mockMvc.perform(delete("/v1/task/deleteTask?taskId=1").contentType(MediaType.APPLICATION_JSON)
                .param("taskId", "1"))
                .andExpect(status().isOk());

    }

    @Test
    public void updateTask() throws Exception {

        //Given
        TaskDto taskDto = new TaskDto(1L, "Test titleDto", "Test contentDto");
        Task task = new Task(1L, "Test title", "Test content");

        when(taskMapper.mapToTaskDto(ArgumentMatchers.any(Task.class))).thenReturn(taskDto);
        when(service.saveTask(ArgumentMatchers.any(Task.class))).thenReturn(task);

        //When & Then
        mockMvc.perform(put("/v1/task/updateTask").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1L)))
                .andExpect(jsonPath("$[0].title", is("Test update")))
                .andExpect(jsonPath("$[0].content", is("Test content update")));

    }

    @Test
    public void createTask() throws Exception {

        //Given
        Task task = new Task(1L, "Test title", "Test content");
        TaskDto taskDto = new TaskDto(1L, "Test titleDto", "Test contentDto");

        when(taskMapper.mapToTask(ArgumentMatchers.any(TaskDto.class))).thenReturn(task);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        //When & Then
        mockMvc.perform(post("/v1/task/createTask").contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andExpect(status().isOk());
    }

}