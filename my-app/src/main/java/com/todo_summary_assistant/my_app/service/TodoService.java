package com.todo_summary_assistant.my_app.service;

import com.todo_summary_assistant.my_app.dto.TodoResponse;
import com.todo_summary_assistant.my_app.model.Todo;
import com.todo_summary_assistant.my_app.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TodoService {
    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<TodoResponse> getAllTodos() {
        return todoRepository.findAll()
                .stream()
                .map(todo -> TodoResponse.builder()
                        .id(todo.getId())
                        .task(todo.getTask())
                        .completed(todo.isCompleted())
                        .build())
                .collect(Collectors.toList());
    }

    public TodoResponse addTodo(String task) {
        Todo todo = Todo.builder()
                .task(task)
                .completed(false)
                .build();
        Todo saved = todoRepository.save(todo);
        return TodoResponse.builder()
                .id(saved.getId())
                .task(saved.getTask())
                .completed(saved.isCompleted())
                .build();
    }

    public void deleteTodo(Long id) {
        todoRepository.deleteById(id);
    }

    public List<Todo> getRawTodos() {
        return todoRepository.findAll();
    }
}


