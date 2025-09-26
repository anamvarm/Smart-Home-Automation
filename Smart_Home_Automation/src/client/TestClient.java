package client;

import framework.AbstractClient;

public class TestClient extends AbstractClient {
    
    public TestClient(String host, int port) {
        super(host, port);
    }

    @Override
    protected void handleMessageFromServer(Object message) {
        System.out.println("📨 Received from server: " + message);
    }

    public static void main(String[] args) {
        try {
            TestClient client = new TestClient("localhost", 8300);
            System.out.println("🔗 Connecting to server...");
            client.openConnection();
            
            // Send a test message
            System.out.println("📤 Sending test message to server...");
            client.sendToServer("Hello Server!");
            
            // Keep the client running for a bit to receive response
            Thread.sleep(3000);
            
            client.closeConnection();
            System.out.println("✅ Test completed successfully!");
            
        } catch (Exception e) {
            System.err.println("💥 Client error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}