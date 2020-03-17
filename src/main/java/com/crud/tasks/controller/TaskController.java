package com.crud.tasks.controller;

import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/task")
public class TaskController {

@Autowired
private DbService service;

@Autowired
private TaskMapper taskMapper;

    @RequestMapping(method = RequestMethod.GET, value = "/getTasks")
    public List<TaskDto> getTasks() {
        return new taskMapper.mapToTaskDtoList(service.getAllTasks());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/tasks/{taskId}")
    public TaskDto getTask(Long taskId) {
        return new TaskDto(1L, "test title", "test content");
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/tasks/{taskId}")
    public void deleteTask(Long taskId) {
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/tasks{/taskDto}")
    public TaskDto updateTask(TaskDto taskDto) {
        return new TaskDto(1L, "Edited test title", "test content 2");
    }

    @RequestMapping(method = RequestMethod.POST, value = "/tasks{/taskDto}")
    public void createTask(TaskDto taskDto) {

    }
}
