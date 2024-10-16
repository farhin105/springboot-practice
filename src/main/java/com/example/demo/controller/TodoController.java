package com.example.demo.controller;

import com.example.demo.models.Todo;
import com.example.demo.services.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "/todos")
public class TodoController {
    private final TodoService todoService;

    @Autowired
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public Todo[] getTodos () {

        return todoService.getTodos();

    }

    @GetMapping(path = "/gson")
    public List<Todo> getTodosGson () {

        return todoService.getTodosGson();

    }
}
