package framework;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Abstract base class for TCP servers
 * Handles multiple client connections and message routing
 */
public abstract class AbstractServer {
    private ServerSocket serverSocket;
    private int port;
    private boolean listening = true;
    private List<ClientHandler> clients = Collections.synchronizedList(new ArrayList<>());

    public AbstractServer(int port) {
        this.port = port;
    }

    /**
     * Starts the server and begins listening for client connections
     */
    public void listen() throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("Server listening on port " + port);
        
        while (listening) {
            Socket clientSocket = serverSocket.accept();
            ClientHandler clientHandler = new ClientHandler(clientSocket, this);
            clients.add(clientHandler);
            new Thread(clientHandler).start();
            clientConnected(clientSocket);
        }
    }

    /**
     * Stops the server
     */
    public void stop() throws IOException {
        listening = false;
        serverSocket.close();
        for (ClientHandler client : clients) {
            // Close client connections
        }
    }

    // Abstract methods to be implemented by concrete servers
    protected abstract void handleMessageFromClient(Object message, Socket clientSocket);
    
    // Optional hooks for subclasses
    protected void clientConnected(Socket clientSocket) {
        System.out.println("New client connected: " + clientSocket.getInetAddress());
    }
    
    protected void clientDisconnected(Socket clientSocket) {
        System.out.println("Client disconnected: " + clientSocket.getInetAddress());
    }

    /**
     * Send a message to a specific client
     */
    public void sendToClient(Socket clientSocket, Object message) throws IOException {
    	ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
        out.writeObject(message);
        out.flush();
    }

    /**
     * Broadcast a message to all connected clients
     */
    public void broadcastToAllClients(Object message) throws IOException {
        synchronized (clients) {
            for (ClientHandler client : clients) {
                if (client.isConnected()) {
                    sendToClient(client.getSocket(), message);
                }
            }
        }
    }

    // Inner class to handle individual client connections
    private class ClientHandler implements Runnable {
        private Socket clientSocket;
        private AbstractServer server;
        private boolean connected = true;

        public ClientHandler(Socket socket, AbstractServer server) {
            this.clientSocket = socket;
            this.server = server;
        }

        public Socket getSocket() { return clientSocket; }
        public boolean isConnected() { return connected; }

        @Override
        public void run() {
            try (ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream())) {
                while (connected) {
                	Object message = in.readObject();
                    server.handleMessageFromClient(message, clientSocket);
                }
            } catch (Exception e) {
                connected = false;
                clients.remove(this);
                server.clientDisconnected(clientSocket);
            }
        }
    }
}