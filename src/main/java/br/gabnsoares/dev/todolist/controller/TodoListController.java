package br.gabnsoares.dev.todolist.controller;

import br.gabnsoares.dev.todolist.controller.dto.TaskDescription;
import br.gabnsoares.dev.todolist.domain.Task;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tasks")
public class TodoListController {

    private List<Task> tasks = new ArrayList<>();

    @GetMapping
    public ResponseEntity<List<Task>> listTasks() {
        return ResponseEntity.ok(tasks);

    }

    @PostMapping
    public void createTask(@RequestBody Task task) {
        tasks.add(task);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        tasks.removeIf(task -> task.id().equals(taskId));
        return ResponseEntity.noContent().build();

    }

    @PutMapping("/{taskId}")
    public ResponseEntity<Void> updateTask(@PathVariable Long taskId,
                                           @RequestBody TaskDescription dto) {
        tasks = tasks.stream()
                .map(task -> {
                    if (task.id().equals(taskId)) {
                        return new Task(task.id(), dto.description());
                    }
                    return task;
                }).collect(Collectors.toCollection(ArrayList::new));

        return ResponseEntity.noContent().build();

    }

    @DeleteMapping
    public ResponseEntity<Void> clearTasks() {
        tasks = new ArrayList<>();
        return ResponseEntity.noContent().build();
    }
}
