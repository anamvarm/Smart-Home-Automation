package common;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Simple JSON-like serialization for Message objects
 */
public class JsonUtils {
    
    public static String toJson(Message message) {
        StringBuilder json = new StringBuilder();
        json.append("{");
        
        try {
            Field[] fields = Message.class.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true);
                Object value = fields[i].get(message);
                
                if (value != null) {
                    if (i > 0) json.append(",");
                    json.append("\"").append(fields[i].getName()).append("\":");
                    
                    if (value instanceof String) {
                        json.append("\"").append(escapeJson(value.toString())).append("\"");
                    } else if (value instanceof Enum) {
                        json.append("\"").append(value.toString()).append("\"");
                    } else {
                        json.append(value);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        json.append("}");
        return json.toString();
    }
    
    public static Message fromJson(String json) {
        Message message = new Message(CommandType.GET_STATUS); // Default command
        
        try {
            // Simple JSON parsing
            json = json.trim().substring(1, json.length() - 1);
            String[] pairs = json.split(",");
            
            for (String pair : pairs) {
                String[] keyValue = pair.split(":", 2);
                if (keyValue.length == 2) {
                    String key = keyValue[0].trim().replace("\"", "");
                    String value = keyValue[1].trim().replace("\"", "");
                    
                    setField(message, key, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return message;
    }
    
    private static void setField(Message message, String fieldName, String value) {
        try {
            Field field = Message.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            
            if (field.getType() == CommandType.class) {
                field.set(message, CommandType.valueOf(value));
            } else if (field.getType() == DeviceType.class) {
                field.set(message, DeviceType.valueOf(value));
            } else if (field.getType() == boolean.class) {
                field.set(message, Boolean.parseBoolean(value));
            } else if (field.getType() == String.class) {
                field.set(message, value);
            }
        } catch (Exception e) {
            // Ignore fields we can't set
        }
    }
    
    private static String escapeJson(String str) {
        return str.replace("\\", "\\\\")
                  .replace("\"", "\\\"")
                  .replace("\n", "\\n")
                  .replace("\r", "\\r")
                  .replace("\t", "\\t");
    }
}