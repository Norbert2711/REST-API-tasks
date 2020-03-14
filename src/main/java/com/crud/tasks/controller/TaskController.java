package com.crud.tasks.controller;

import com.crud.tasks.domain.TaskDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/task")
public class TaskController {

    @RequestMapping(method = RequestMethod.GET, value = "getTask")
    public List<TaskDto> getTasks() {
        return new ArrayList<>();
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
