package controller;

import model.Todo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.TodoService;

@RestController
@RequestMapping("/todos")
public class TodoController {
    private TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }
}
