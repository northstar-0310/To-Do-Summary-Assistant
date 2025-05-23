package com.todo_summary_assistant.my_app.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "To_do_list")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String task;

    private boolean completed;

}

