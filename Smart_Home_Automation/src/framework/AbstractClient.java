package framework;

import java.io.*;
import java.net.*;

/**
 * Abstract base class for TCP clients
 * Handles connection to server and message exchange
 */
public abstract class AbstractClient {
    private Socket clientSocket;
    private String host;
    private int port;
    private ObjectOutputStream outputStream;
    private boolean connected = false;

    public AbstractClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * Establishes connection to the server
     */
    public void openConnection() throws IOException {
        clientSocket = new Socket(host, port);
        outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        connected = true;
        
        // Start listener thread for incoming messages
        new Thread(new ServerListener()).start();
        connectionEstablished();
    }

    /**
     * Closes the connection to the server
     */
    public void closeConnection() throws IOException {
        connected = false;
        if (outputStream != null) outputStream.close();
        if (clientSocket != null) clientSocket.close();
        connectionClosed();
    }

    /**
     * Sends a message to the server
     */
    public void sendToServer(Object message) throws IOException {
        if (outputStream != null && connected) {
            outputStream.writeObject(message);
            outputStream.flush();
        }
    }

    public boolean isConnected() { return connected; }

    // Abstract method to handle incoming messages from server
    protected abstract void handleMessageFromServer(Object message);
    
    // Optional hooks for subclasses
    protected void connectionEstablished() {
        System.out.println("Connected to server at " + host + ":" + port);
    }
    
    protected void connectionClosed() {
        System.out.println("Disconnected from server");
    }

    // Inner class to listen for server messages
    private class ServerListener implements Runnable {
        @Override
        public void run() {
            try (ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream())) {
                System.out.println("🔍 ServerListener started, waiting for messages...");
                while (connected) {
                    Object message = in.readObject();
                    System.out.println("🔍 Received message in ServerListener: " + message);
                    handleMessageFromServer(message);
                }
            } catch (Exception e) {
                connected = false;
                System.out.println("Connection to server lost: " + e.getMessage());
            }
        }
    }
}
