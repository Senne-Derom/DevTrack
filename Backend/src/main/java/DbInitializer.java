import jakarta.annotation.PostConstruct;
import model.Todo;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import repository.TodoRepository;

@Component
public class DbInitializer {
    private TodoRepository todoRepository;

    public DbInitializer(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @PostConstruct
    public void initialize() {
        try {
            Todo todo1 = new Todo("Buy groceries", "Milk, Bread, Eggs");
            Todo todo2 = new Todo("Finish project", "Complete the backend API");
            Todo todo3 = new Todo("Call mom", "Check in and say hi");
            todoRepository.save(todo1);
            todoRepository.save(todo2);
            todoRepository.save(todo3);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
