package server;

import framework.AbstractServer;
import java.net.Socket;

public class TestServer extends AbstractServer {
    
    public TestServer(int port) {
        super(port);
    }

    @Override
    protected void handleMessageFromClient(Object message, Socket clientSocket) {
        System.out.println("📨 Received from client: " + message);
        
        try {
            // Echo the message back to the client
            sendToClient(clientSocket, "Echo: " + message);
            System.out.println("✅ Echoed back to client");
        } catch (Exception e) {
            System.err.println("❌ Error sending to client: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            TestServer server = new TestServer(8300);
            System.out.println("🚀 Starting Test Server on port 8300...");
            server.listen();
        } catch (Exception e) {
            System.err.println("💥 Failed to start server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}