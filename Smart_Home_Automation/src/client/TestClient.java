package client;

import framework.AbstractClient;

public class TestClient extends AbstractClient {
    
    public TestClient(String host, int port) {
        super(host, port);
    }

    @Override
    protected void handleMessageFromServer(Object message) {
        System.out.println("📨 Server response: " + message);
    }

    public static void main(String[] args) {
        try {
            TestClient client = new TestClient("localhost", 8300);
            System.out.println("🔗 Connecting to server...");
            client.openConnection();
            
            Thread.sleep(1000);
            
            // Test 1: Plain text
            System.out.println("\n📤 Test 1: Sending plain text...");
            client.sendToServer("Hello Server!");
            Thread.sleep(2000);
            
            // Test 2: Structured command as string
            System.out.println("\n📤 Test 2: Sending GET_STATUS command...");
            client.sendToServer("GET_STATUS|LIGHT|kitchen-light");
            Thread.sleep(2000);
            
            // Test 3: Another structured command
            System.out.println("\n📤 Test 3: Sending TURN_ON command...");
            client.sendToServer("TURN_ON|LIGHT|living-room-light");
            Thread.sleep(2000);
            
            Thread.sleep(3000);
            client.closeConnection();
            System.out.println("\n✅ All tests completed!");
            
        } catch (Exception e) {
            System.err.println("💥 Client error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}