package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

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

    @Autowired
    private DbService service;

    @Autowired
    private TaskMapper taskMapper;

    @Test
    public void getTask() throws Exception {

        //Given
        TaskDto taskDto = new TaskDto(1L, "Test titleDto", "Test contentDto");
        Task task = new Task(1L, "Test title", "Test content");

        when(taskMapper.mapToTaskDto(ArgumentMatchers.any(Task.class))).thenReturn(taskDto);
        when(service.getTask(1L)).thenReturn(Optional.of(task));

        //When & Then
        mockMvc.perform(get("/v1/tasks/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is("1")))
                .andExpect(jsonPath("$[0].name", is("Test titleDto")))
                .andExpect(jsonPath("$[0].content", is("Test contentDto")));

    }

    @Test
    public void deleteTask() throws Exception {

        //Given
        Task task = new Task(1L, "Test title", "Test content");

        when(service.getTask(1L)).thenReturn(Optional.of(task));

        //When & Then
        mockMvc.perform(delete("/v1/tasks/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    public void updateTask() throws Exception {

        //Given
        TaskDto taskDto = new TaskDto(1L, "Test titleDto", "Test contentDto");
        Task task = new Task(1L, "Test title", "Test content");

        when(taskMapper.mapToTaskDto(ArgumentMatchers.any(Task.class))).thenReturn(taskDto);
        when(service.saveTask(ArgumentMatchers.any(Task.class))).thenReturn(task);
        when(taskMapper.mapToTask(ArgumentMatchers.any(TaskDto.class))).thenReturn(task);

        Gson gson = new Gson();
        String jsonCont = gson.toJson(taskDto);

        //When & Then
        mockMvc.perform(put("/v1/tasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Test")))
                .andExpect(jsonPath("$.content", is("Test content")));

    }

    @Test
    public void createTask() throws Exception {

        //Given
        TaskDto taskDto = new TaskDto(1L, "Test titleDto", "Test contentDto");
        Task task = new Task(1L, "Test title", "Test content");

        when(taskMapper.mapToTask(ArgumentMatchers.any(TaskDto.class))).thenReturn(task);

        Gson gson = new Gson();

        //When & Then
        mockMvc.perform(post("/v1/tasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}