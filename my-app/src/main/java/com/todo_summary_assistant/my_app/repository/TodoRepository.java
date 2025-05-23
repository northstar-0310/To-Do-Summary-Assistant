package com.todo_summary_assistant.my_app.repository;

import com.todo_summary_assistant.my_app.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {}
