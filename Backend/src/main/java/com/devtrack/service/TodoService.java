package com.devtrack.service;

import com.devtrack.model.Todo;
import org.springframework.stereotype.Service;
import com.devtrack.repository.TodoRepository;

import java.util.List;

@Service
public class TodoService {
    private TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }
}
