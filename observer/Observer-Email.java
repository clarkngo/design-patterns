/**
Design Pattern - Observer
All classes and interfaces are in a single file for quick copy-paste and test run of the code.

You may represent the classes and interfaces in separate Java files:

publisher
|_ EventManager.java

editor
|_ Editor.java

listeners
|_ EventListener.java
|_ EmailNotificationListener.java
|_ LogOpenListener.java

Main.java

Source: https://refactoring.guru/design-patterns/observer/java/example
**/

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// The base publisher class includes subscription management
// code and notification methods.
class EventManager {
  Map<String, List<EventListener>> listeners = new HashMap<>();

  public EventManager(String... operations) {
    for (String operation : operations) {
      this.listeners.put(operation, new ArrayList<>());

    }
  }

  public void subscribe(String eventType, EventListener listener) {
    List<EventListener> users = listeners.get(eventType);
    users.remove(listener);
  }

  public void notify(String eventType, File file) {
    List<EventListener> users = listeners.get(eventType);
    for (EventListener listener : users) {
      listener.update(eventType, file);
    }
  }
}

// The concrete publisher contains real business logic that's
// interesting for some subscribers. We could derive this class
// from the base publisher, but that isn't always possible in
// real life because the concrete publisher might already be a
// subclass. In this case, you can patch the subscription logic
// in with composition, as we did here.
class Editor {
  public EventManager events;
  private File file;

  public Editor() {
    this.events = new EventManager("open", "save");
  }

  public void openFile(String filePath) {
    this.file = new File(filePath);
    events.notify("open", file);
  }

  public void saveFile() throws Exception {
    if (this.file != null) {
      events.notify("save", file);
    } else {
      throw new Exception("Please open a file first.");
    }
  }
}

// Here's the subscriber interface. If your programming language
// supports functional types, you can replace the whole
// subscriber hierarchy with a set of functions.
interface EventListener {
  void update(String eventType, File file);
}

// Concrete subscribers react to updates issued by the publisher
// they are attached to.
class EmailNotificationListener implements EventListener {
  private String email;

  public EmailNotificationListener(String email) {
    this.email = email;
  }

  @Override
  public void update(String eventType, File file) {
    System.out.println("Email to " + email + ": Someone has performed " + eventType + " operation with the following file: " + file.getName());
  }
}

class LogOpenListener implements EventListener {
  private File log;

  public LogOpenListener(String fileName) {
    this.log = new File(fileName);
  }

  @Override
  public void update(String eventType, File file) {
    System.out.println("Save to log " + log + ": Someone has performed " + eventType + " operation with the follwoing file: " + file.getName());
  }
}

// An application can configure publishers and subscribers at
// runtime.
class Main {
  public static void main(String[] args) {
    Editor editor = new Editor();
    editor.events.subscribe("open", new LogOpenListener("/path/to/log/file.txt"));
    editor.events.subscribe("save", new EmailNotificationListener("admin@example.com"));

    try {
      editor.openFile("test.txt");
      editor.saveFile();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
