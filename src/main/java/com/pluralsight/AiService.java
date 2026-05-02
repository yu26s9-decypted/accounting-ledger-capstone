package com.pluralsight;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class AiService {
    public static void callOpenAPI(String userText) {
        String openRouterKey = System.getenv("OPENROUTER_API_KEY");
        HttpClient client = HttpClient.newHttpClient();


        String requestBody = String.format("""
                {
                  "model": "google/gemini-2.5-flash",
                  "messages": [
                
                    {
                      "role": "user",
                      "content": "%s"
                    },
                    {
                      "role" : "system"
                      "content": "You are a expert financial advisor. Do not include any markdown language. It must be in plain text."
                    }
                  ]
                }
                """, userText);


        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://openrouter.ai/api/v1/chat/completions"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + openRouterKey)
                .POST(HttpRequest.BodyPublishers.ofString((requestBody)))
                .build();


        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper map = new ObjectMapper();
            JsonNode r = map.readTree(response.body());
            String message = r
                    .path("choices")
                    .get(0)
                    .path("message")
                    .path("content")
                    .asText();

            System.out.println(message);



        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        }

    }

}

