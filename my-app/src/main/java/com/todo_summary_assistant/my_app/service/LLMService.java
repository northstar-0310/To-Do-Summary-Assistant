package com.todo_summary_assistant.my_app.service;

import com.todo_summary_assistant.my_app.model.Todo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LLMService {

    private final RestTemplate restTemplate;

    @Value("${cohere.api.key}")
    private String cohereApiKey;

    public String generateSummary(List<Todo> todos) {
        String tasks = todos.stream()
                .filter(todo -> !todo.isCompleted())
                .map(todo -> "- " + todo.getTask())
                .collect(Collectors.joining("\n"));

        String prompt = "Summarize the following pending to-dos as bullet points:\n" + tasks;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(cohereApiKey);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "command-r-plus");  // or "command" (free-tier)
        requestBody.put("prompt", prompt);
        requestBody.put("max_tokens", 150);
        requestBody.put("temperature", 0.5);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(
                "https://api.cohere.ai/v1/generate", request, Map.class
        );

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            return response.getBody().get("generations") != null
                    ? ((List<Map<String, String>>) response.getBody().get("generations")).get(0).get("text")
                    : "No summary available.";
        }

        return "Failed to generate summary.";
    }
}
