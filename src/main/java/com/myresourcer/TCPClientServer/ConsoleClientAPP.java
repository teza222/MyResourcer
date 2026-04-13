package com.myresourcer.TCPClientServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Base64;
import java.util.Scanner;

public class ConsoleClientAPP {

    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 8888;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("--- TCP Server Engine Authentication ---");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        // Encode credentials to Base64 to send to server
        String authString = username + ":" + password;
        String encodedAuth = Base64.getEncoder().encodeToString(authString.getBytes());

        System.out.println("\nConnecting to TCP Server Engine at " + SERVER_ADDRESS + ":" + SERVER_PORT + "...");

        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // Read welcome message from server
            String response = in.readLine();
            if (response != null) {
                System.out.println("Server: " + response);
            }

            // Send AUTH command first
            out.println("AUTH " + encodedAuth);
            response = in.readLine();
            System.out.println("Server: " + response);

            if (response != null && response.contains("ERROR")) {
                System.out.println("Authentication setup failed. Exiting...");
                return;
            }

            printMenu();

            while (true) {
                System.out.print("\nSelect an option (1-4, 0 to Exit): ");
                String choice = scanner.nextLine();

                String command = null;
                switch (choice) {
                    case "1": // Create Mock Asset
                        System.out.println("\n--- Creating Asset with Mock Data ---");
                        String mockJson = "{\"item\":\"Mock Asset (TCP)\", \"serialNumber\":\"MOCK-123\", \"categoryId\":1, \"mobile\":false}";
                        command = "POST /assets " + mockJson;
                        break;

                    case "2": // Read all Assets
                        command = "GET /assets";
                        break;

                    case "3": // Update Asset by ID
                        System.out.print("Enter Asset ID to update: ");
                        String updateId = scanner.nextLine();
                        System.out.println("Updating with mock data...");
                        String updateMockJson = "{\"item\":\"Updated Mock Asset\", \"serialNumber\":\"MOCK-999\", \"categoryId\":1, \"mobile\":true}";
                        command = "PUT /assets/" + updateId + " " + updateMockJson;
                        break;

                    case "4": // Delete Asset by ID
                        System.out.print("Enter Asset ID to delete: ");
                        String deleteId = scanner.nextLine();
                        command = "DELETE /assets/" + deleteId;
                        break;

                    case "0":
                    case "EXIT":
                    case "exit":
                        out.println("EXIT");
                        System.out.println("Exiting client...");
                        return;

                    default:
                        System.out.println("Invalid option. Try again.");
                        continue;
                }

                if (command != null) {
                    out.println(command);
                    response = in.readLine();
                    if (response == null) {
                        System.out.println("Server closed connection.");
                        break;
                    }
                    System.out.println("\nServer Response: " + response);
                }
            }

        } catch (IOException e) {
            System.err.println("Error connecting to server: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void printMenu() {
        System.out.println("\n<>=<>=<>=<>=<>=<>=<>=<>=<>=<>=<>=<>=<>");
        System.out.println("   ASSET MANAGEMENT CLIENT");
        System.out.println("<>=<>=<>=<>=<>=<>=<>=<>=<>=<>=<>=<>=<>");
        System.out.println("1. Create Asset");
        System.out.println("2. View All Assets");
        System.out.println("3. Update Asset");
        System.out.println("4. Delete Asset");
        System.out.println("0. Exit");
        System.out.println("<>=<>=<>=<>=<>=<>=<>=<>=<>=<>=<>=<>=<>");
    }
}
