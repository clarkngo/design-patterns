/**
Design Pattern - Command

All classes and interfaces are in a single file for quick copy-paste and test run of the code.

You may represent the classes and interfaces in separate Java files:

commands
|_ Command.java
|_ LightOnOffCommand.java

receiver
|_ Light.java

invoker
|_SimpleRemoteControl.java

Main.java
**/

// Command declares and interface for all commands
interface Command {
  public void execute();
}
  
// ConcreteCommand defintes binding between action (on() and off()) and a Receiver (Light)
class LightOnOffCommand implements Command {
  Light light;
  public LightOnOffCommand(Light light) {
    this.light = light;
  }

  public void execute() {
    if (this.light.getLightState()) {
      this.light.off();
      return;
    }
    this.light.on();
  }
}
  
// Receiver (Light) knows how to perform the work needed to carryout the request. 
// Any class can act as a Receiver 
class Light {
  private boolean isOn = false;

  public Light() {
    this.isOn = false;
  }

  boolean getLightState() { return this.isOn; }
  void setLightState() {this.isOn = !this.isOn;}

  void on() {
    this.setLightState();
    System.out.println("Light is on! It's so BRIGHT!");
  }
  void off() {
    this.setLightState();
    System.out.println("Light is off! It's so DARK!");
  }
}
  
// Invoker (SimpleRemoteControl) holds a command and 
// at some point asks the command to carry out a request
// by calling its execute() method
class SimpleRemoteControl {
  Command slot;
  public SimpleRemoteControl() {}

  public void setCommand(Command command) {
    slot = command;
  }

  public void buttonWasPressed() {
    slot.execute();
  }
}
  
// Client (Main) is reponsible for creating 
// a ConcreteCommand (LightOnOffCommand)
// and setting its receiver  
class Main {
  public static void main(String[] args) {
    SimpleRemoteControl remote = new SimpleRemoteControl();
    Light light = new Light();
    LightOnOffCommand lightOn = new LightOnOffCommand(light);
    remote.setCommand(lightOn);
    remote.buttonWasPressed();
    remote.buttonWasPressed();
    remote.buttonWasPressed();
  }
}
  