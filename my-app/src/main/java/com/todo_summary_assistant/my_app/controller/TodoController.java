package com.todo_summary_assistant.my_app.controller;

import com.todo_summary_assistant.my_app.dto.TodoRequest;
import com.todo_summary_assistant.my_app.dto.TodoResponse;
import com.todo_summary_assistant.my_app.service.LLMService;
import com.todo_summary_assistant.my_app.service.SlackService;
import com.todo_summary_assistant.my_app.service.TodoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TodoController {

    private final TodoService todoService;
    private final LLMService llmService;
    private final SlackService slackService;

    public TodoController(TodoService todoService, LLMService llmService, SlackService slackService) {
        this.todoService = todoService;
        this.llmService = llmService;
        this.slackService = slackService;
    }

    @GetMapping("/todos")
    public ResponseEntity<List<TodoResponse>> getTodos() {
        return ResponseEntity.ok(todoService.getAllTodos());
    }

    @PostMapping("/todos")
    public ResponseEntity<TodoResponse> addTodo(@RequestBody TodoRequest request) {
        return ResponseEntity.ok(todoService.addTodo(request.getTask()));
    }

    @DeleteMapping("/todos/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/summarize")
    public ResponseEntity<String> summarizeAndSend() {
        String summary = llmService.generateSummary(todoService.getRawTodos());
        slackService.sendSummaryToSlack(summary);
        return ResponseEntity.ok(summary);
    }
}