package common;

/**
 * Types of commands that can be sent between client and server
 */
public enum CommandType {
    // Device control commands
    TURN_ON,
    TURN_OFF,
    TOGGLE,
    SET_VALUE,
    GET_STATUS,
    
    // Authentication commands
    LOGIN,
    LOGOUT,
    REGISTER,
    
    // System commands
    DISCOVER_DEVICES,
    CREATE_AUTOMATION,
    DELETE_AUTOMATION,
    GET_NOTIFICATIONS
}