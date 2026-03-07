package com.devtrack;

import com.devtrack.model.Project;
import com.devtrack.model.Purpose;
import com.devtrack.model.Todo;
import com.devtrack.repository.ProjectRepository;
import com.devtrack.repository.TodoRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class DbInitializer {
    private final ProjectRepository projectRepository;
    private final TodoRepository todoRepository;

    public DbInitializer(ProjectRepository projectRepository, TodoRepository todoRepository) {
        this.projectRepository = projectRepository;
        this.todoRepository = todoRepository;
    }

    @PostConstruct
    public void initialize() {
        try {
            // Create projects first (without todos list, we link via Todo side)
            Project devTrack = new Project("DevTrack", "A developer task tracking application", Purpose.FREE_TIME, List.of());
            Project schoolWork = new Project("School Assignments", "All school-related tasks and deadlines", Purpose.SCHOOL, List.of());
            projectRepository.save(devTrack);
            projectRepository.save(schoolWork);

            // Create todos linked to their project
            Todo todo1 = new Todo("Set up database", "Create schema and initialize tables", new Date(), "IN_PROGRESS", devTrack);
            Todo todo2 = new Todo("Build REST API", "Implement all backend endpoints", new Date(), "TODO", devTrack);
            Todo todo3 = new Todo("Write unit tests", "Cover service and controller layers", new Date(), "TODO", devTrack);

            Todo todo4 = new Todo("Math homework", "Complete exercises from chapter 5", new Date(), "TODO", schoolWork);
            Todo todo5 = new Todo("History essay", "Write 1000-word essay on WW2", new Date(), "IN_PROGRESS", schoolWork);

            todoRepository.saveAll(List.of(todo1, todo2, todo3, todo4, todo5));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
