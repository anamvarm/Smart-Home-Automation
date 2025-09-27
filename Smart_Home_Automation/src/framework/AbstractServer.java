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
        // Find the client handler for this socket and use its output stream
        synchronized (clients) {
            for (ClientHandler client : clients) {
                if (client.getSocket().equals(clientSocket) && client.isConnected()) {
                    ObjectOutputStream out = client.getOutputStream();
                    if (out != null) {
                        out.writeObject(message);
                        out.flush();
                        return;
                    }
                }
            }
        }
        throw new IOException("Client not found or not connected");
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
        private ObjectOutputStream outputStream;

        public ClientHandler(Socket socket, AbstractServer server) {
            this.clientSocket = socket;
            this.server = server;
        }

        public Socket getSocket() { return clientSocket; }
        public boolean isConnected() { return connected; }
        public ObjectOutputStream getOutputStream() { return outputStream; }

        @Override
        public void run() {
            try (ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream())) {
                // Create the output stream once and reuse it
                outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                
                while (connected) {
                	Object message = in.readObject();
                    server.handleMessageFromClient(message, clientSocket);
                }
            } catch (Exception e) {
                connected = false;
                clients.remove(this);
                server.clientDisconnected(clientSocket);
            } finally {
                try {
                    if (outputStream != null) {
                        outputStream.close();
                    }
                } catch (IOException e) {
                    // Ignore close errors
                }
            }
        }
    }
}
//cccc