package com.myresourcer.TCPClientServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TCPServerEngine {

    private static final int PORT = 8888;
    private static final int THREAD_POOL_SIZE = 10;
    private boolean isRunning = true;
    private ServerSocket serverSocket;
    private ExecutorService threadPool;

    public void start() {
        try {
            serverSocket = new ServerSocket(PORT);
            threadPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

            System.out.println("TCP Server Engine started on port " + PORT);
            System.out.println("Accepting client connections...");

            while (isRunning) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Client connected: " + clientSocket.getInetAddress());

                    // Assign the client to a worker thread
                    ClientHandler clientHandler = new ClientHandler(clientSocket);
                    threadPool.execute(clientHandler);

                } catch (IOException e) {
                    if (isRunning) {
                        System.err.println("Error accepting client connection: " + e.getMessage());
                    }
                }
            }

        } catch (IOException e) {
            System.err.println("Could not listen on port " + PORT);
            e.printStackTrace();
        }
    }

    public void stop() {
        isRunning = false;
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
            if (threadPool != null) {
                threadPool.shutdown();
            }
            System.out.println("TCP Server Engine stopped.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        TCPServerEngine server = new TCPServerEngine();
        server.start();
    }
}
