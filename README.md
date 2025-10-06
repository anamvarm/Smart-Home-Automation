# 🏠 Smart Home Automation System

A comprehensive Java-based smart home automation platform that provides centralized control and management of various smart devices through a client-server architecture.

## 🌟 Features

### Core Functionality
- **Device Management**: Control and monitor various smart home devices
- **User Authentication**: Secure login system with role-based access control
- **Automation Engine**: Create and manage automation rules for smart devices
- **Real-time Monitoring**: Live status updates and device state tracking
- **Notification System**: Alert system for device events and automation triggers
- **GUI Interface**: Modern Swing-based user interface for easy device control

### Supported Device Types
- 💡 **Smart Lights** - Brightness control, color settings, dimming
- 🌡️ **Thermostats** - Temperature control and scheduling
- 🔒 **Smart Locks** - Door lock control and security management
- 🚨 **Smoke Detectors** - Fire safety monitoring and alerts
- 📹 **Security Cameras** - Surveillance and monitoring
- 🔌 **Smart Plugs** - Power control for connected devices
- 👁️ **Motion Sensors** - Motion detection and automation triggers

### Automation Capabilities
- **Motion-activated lighting** - Lights turn on when motion is detected
- **Temperature-based controls** - HVAC adjustments based on temperature readings
- **Security alerts** - Immediate notifications for smoke detection
- **Scheduled actions** - Time-based automation rules
- **Custom triggers** - User-defined automation conditions

## 🏗️ Architecture

### Client-Server Model
- **Server**: Centralized device management and automation engine
- **Client**: User interface and device control applications
- **Communication**: TCP socket-based messaging system

### Key Components

#### Server Side
- `SmartHomeServer` - Main server handling client connections
- `DeviceManager` - Device registration and control management
- `AuthenticationManager` - User authentication and session management
- `AutomationEngine` - Rule processing and automation execution
- `NotificationService` - Alert and notification handling

#### Client Side
- `ControlHubClient` - Command-line interface for device control
- `MainWindow` - GUI application with tabbed interface
- `DeviceController` - Device interaction and state management
- Various panels for different functionalities (Dashboard, Devices, Automation, etc.)

## 🚀 Getting Started

### Prerequisites
- Java 8 or higher
- Any modern operating system (Windows, macOS, Linux)

### Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd Smart_Home_Automation
   ```

2. **Compile the project**
   ```bash
   javac -d bin -cp src src/**/*.java
   ```

### Running the Application

#### Start the Server
```bash
java -cp bin server.SmartHomeServer
```
The server will start on port 8300 by default.

#### Run the GUI Client
```bash
java -cp bin client.gui.MainWindow
```

#### Run the Command-Line Client
```bash
java -cp bin client.ControlHubClient
```

## 🔧 Usage

### Default Credentials
- **Admin User**: `admin` / `admin123`
- **Regular User**: `user` / `user123`

### GUI Application Features

#### Login Panel
- Secure authentication with username and password
- Automatic session management

#### Dashboard Panel
- Overview of all connected devices
- Quick status monitoring
- System statistics

#### Device Panel
- Individual device control
- Real-time status updates
- Device property management

#### Automation Panel
- Create and manage automation rules
- View automation statistics
- Enable/disable automation rules

#### Notification Panel
- View system alerts and notifications
- Filter notifications by type and priority

### Command-Line Interface

The CLI provides interactive menus for:
- User authentication
- Device control (turn on/off, status check)
- Device discovery
- Session management

## 📁 Project Structure

```
Smart_Home_Automation/
├── src/
│   ├── client/                 # Client-side code
│   │   ├── gui/               # GUI components
│   │   ├── ControlHubClient.java
│   │   └── DeviceController.java
│   ├── server/                # Server-side code
│   │   ├── devices/           # Device implementations
│   │   ├── SmartHomeServer.java
│   │   ├── DeviceManager.java
│   │   ├── AutomationEngine.java
│   │   └── AuthenticationManager.java
│   ├── common/                # Shared classes
│   │   ├── Message.java
│   │   ├── DeviceType.java
│   │   └── User.java
│   ├── framework/             # Base framework classes
│   └── config/                # Configuration classes
├── bin/                       # Compiled classes
└── README.md
```

## 🔌 Device Integration

### Adding New Devices

1. Create a new device class extending `Device`
2. Implement device-specific properties and methods
3. Register the device type in `DeviceType` enum
4. Add device initialization in `DeviceManager`

### Example Device Implementation
```java
public class SmartFan extends Device {
    private int speed;
    private boolean oscillating;
    
    public SmartFan(String deviceId, String name, String location) {
        super(deviceId, DeviceType.FAN, name, location);
    }
    
    public boolean setSpeed(int speed) {
        if (speed >= 0 && speed <= 100) {
            this.speed = speed;
            setProperty("speed", speed);
            return true;
        }
        return false;
    }
}
```

## 🤖 Automation Rules

### Creating Automation Rules

Automation rules consist of:
- **Trigger Device**: Device that initiates the automation
- **Trigger Condition**: Condition that must be met
- **Action Device**: Device that performs the action
- **Action Type**: Type of action to perform

### Example Automation Rules

1. **Motion-Activated Lighting**
   - Trigger: Motion sensor detects movement
   - Action: Turn on lights

2. **Temperature Control**
   - Trigger: Temperature exceeds threshold
   - Action: Adjust thermostat

3. **Security Alert**
   - Trigger: Smoke detector activates
   - Action: Send notification and unlock doors

## 🔒 Security Features

- **User Authentication**: Secure login system
- **Session Management**: Token-based session handling
- **Role-Based Access**: Admin and user permission levels
- **Device Permissions**: Granular control over device access

## 🛠️ Development

### Building from Source
```bash
# Compile all source files
javac -d bin -cp src src/**/*.java

# Run tests
java -cp bin server.TestServer
java -cp bin client.TestClient
```

### Adding New Features

1. **New Device Types**: Extend the `Device` class and add to `DeviceType` enum
2. **New Commands**: Add to `CommandType` enum and implement in server
3. **New Automation Conditions**: Extend `TriggerCondition` enum
4. **GUI Enhancements**: Add new panels or modify existing ones

## 📊 System Requirements

- **Minimum Java Version**: Java 8
- **Memory**: 512MB RAM minimum
- **Storage**: 50MB free space
- **Network**: TCP/IP connectivity for client-server communication

## 🐛 Troubleshooting

### Common Issues

1. **Connection Failed**
   - Ensure server is running before starting client
   - Check firewall settings for port 8300
   - Verify server address and port configuration

2. **Authentication Issues**
   - Use correct default credentials
   - Check user permissions for device access

3. **Device Not Responding**
   - Verify device is properly registered
   - Check device permissions for current user
   - Review server logs for error messages

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Submit a pull request




