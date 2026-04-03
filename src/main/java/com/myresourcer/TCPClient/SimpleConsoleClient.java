package com.myresourcer.TCPClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SimpleConsoleClient {

    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 8888;

    public static void main(String[] args) {
        System.out.println("Connecting to TCP Server Engine at " + SERVER_ADDRESS + ":" + SERVER_PORT + "...");

        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             Scanner scanner = new Scanner(System.in)) {

            // Read welcome message from server
            String response = in.readLine();
            if (response != null) {
                System.out.println("Server: " + response);
            }

            System.out.println("Type your commands (e.g., 'GET /assets/1' or 'EXIT' to quit):");

            while (true) {
                System.out.print("> ");
                String userInput = scanner.nextLine();

                if (userInput.trim().isEmpty()) {
                    continue;
                }

                // Send command to server
                out.println(userInput);

                // Check for exit command locally to break loop cleanly
                if ("EXIT".equalsIgnoreCase(userInput.trim())) {
                    System.out.println("Exiting client...");
                    break;
                }

                // Read response from server
                // Note: The server sends a single line response for now. 
                // If server sends multi-line, we'd need a loop or protocol.
                response = in.readLine();
                if (response == null) {
                    System.out.println("Server closed connection.");
                    break;
                }
                System.out.println("Server Response: " + response);
            }

        } catch (IOException e) {
            System.err.println("Error connecting to server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
