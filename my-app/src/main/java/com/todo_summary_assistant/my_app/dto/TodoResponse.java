package com.todo_summary_assistant.my_app.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoResponse {
    private Long id;
    private String task;
    private boolean completed;
}
