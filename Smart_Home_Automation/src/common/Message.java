package common;

import java.io.Serializable;

/**
 * Standard message format for all client-server communication
 */
public class Message implements Serializable {
    private CommandType command;
    private DeviceType deviceType;
    private String deviceId;
    private Object value;
    private String userId;
    private String sessionToken;
    private boolean success;
    private String errorMessage;
    
    // Constructors
    public Message(CommandType command) {
        this.command = command;
    }
    
    public Message(CommandType command, DeviceType deviceType, String deviceId) {
        this.command = command;
        this.deviceType = deviceType;
        this.deviceId = deviceId;
    }
    
    public Message(CommandType command, DeviceType deviceType, String deviceId, Object value) {
        this.command = command;
        this.deviceType = deviceType;
        this.deviceId = deviceId;
        this.value = value;
    }
    
    // Getters and Setters
    public CommandType getCommand() { return command; }
    public void setCommand(CommandType command) { this.command = command; }
    
    public DeviceType getDeviceType() { return deviceType; }
    public void setDeviceType(DeviceType deviceType) { this.deviceType = deviceType; }
    
    public String getDeviceId() { return deviceId; }
    public void setDeviceId(String deviceId) { this.deviceId = deviceId; }
    
    public Object getValue() { return value; }
    public void setValue(Object value) { this.value = value; }
    
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    
    public String getSessionToken() { return sessionToken; }
    public void setSessionToken(String sessionToken) { this.sessionToken = sessionToken; }
    
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
    
    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
    
    @Override
    public String toString() {
        return "Message{" +
                "command=" + command +
                ", deviceType=" + deviceType +
                ", deviceId='" + deviceId + '\'' +
                ", value=" + value +
                ", success=" + success +
                '}';
    }
}