package server;

import framework.AbstractServer;
import java.net.Socket;

public class TestServer extends AbstractServer {
    
    public TestServer(int port) {
        super(port);
    }

    @Override
    protected void handleMessageFromClient(Object message, Socket clientSocket) {
        System.out.println("📨 Received: " + message);
        
        try {
            // Always work with strings - no custom objects
            if (message instanceof String) {
                String msg = (String) message;
                
                if (msg.startsWith("GET_STATUS|")) {
                    // Format: "GET_STATUS|LIGHT|kitchen-light"
                    String[] parts = msg.split("\\|");
                    String response = "STATUS|ONLINE|" + parts[2] + "|Device is online";
                    sendToClient(clientSocket, response);
                    
                } else if (msg.startsWith("TURN_ON|")) {
                    // Format: "TURN_ON|LIGHT|living-room-light"
                    String[] parts = msg.split("\\|");
                    String response = "SUCCESS|TURN_ON|" + parts[2] + "|Device turned on";
                    sendToClient(clientSocket, response);
                    
                } else {
                    // Plain text echo
                    sendToClient(clientSocket, "ECHO: " + msg);
                }
            }
        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            TestServer server = new TestServer(8300);
            System.out.println("🚀 Starting Simple Test Server on port 8300...");
            server.listen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}