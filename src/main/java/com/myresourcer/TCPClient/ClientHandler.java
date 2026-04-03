package com.myresourcer.TCPClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class ClientHandler implements Runnable {

    private final Socket clientSocket;
    private final HttpClient httpClient;
    private static final String API_BASE_URL = "http://localhost:8080"; // Your Spring Boot URL

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
        this.httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .connectTimeout(Duration.ofSeconds(10))
                .build();
    }

    @Override
    public void run() {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            out.println("CONNECTED TO SERVER ENGINE. FORMAT: [METHOD] [ENDPOINT] [JSON_BODY]");
            
            String requestLine;
            while ((requestLine = in.readLine()) != null) {
                if ("EXIT".equalsIgnoreCase(requestLine.trim())) {
                    out.println("Goodbye.");
                    break;
                }

                System.out.println("Received: " + requestLine);
                String response = sendRequestToSpringBoot(requestLine);
                out.println(response);
            }

        } catch (IOException e) {
            System.err.println("Error handling client: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String sendRequestToSpringBoot(String input) {
        try {
            // Simple parsing: expects "METHOD /path {json}"
            String[] parts = input.split(" ", 3);
            String method = parts[0].toUpperCase();
            String path = parts[1];
            String jsonBody = (parts.length > 2) ? parts[2] : "";

            if (!path.startsWith("/")) path = "/" + path;

            HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                    .uri(URI.create(API_BASE_URL + path))
                    .header("Content-Type", "application/json");

            switch (method) {
                case "GET":
                    requestBuilder.GET();
                    break;
                case "POST":
                    requestBuilder.POST(HttpRequest.BodyPublishers.ofString(jsonBody));
                    break;
                case "PUT":
                    requestBuilder.PUT(HttpRequest.BodyPublishers.ofString(jsonBody));
                    break;
                case "DELETE":
                    requestBuilder.DELETE();
                    break;
                default:
                    return "ERROR: Unsupported Method " + method;
            }

            HttpResponse<String> response = httpClient.send(requestBuilder.build(), HttpResponse.BodyHandlers.ofString());
            return "API_RESPONSE [" + response.statusCode() + "]: " + response.body();

        } catch (Exception e) {
            return "ERROR: " + e.getMessage();
        }
    }
}
