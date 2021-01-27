/**

Design Pattern - Singleton

You may represent the classes and interfaces in separate Java files:

Logger.java
Main.java

**/

final class Logger {
  private static Logger instance;
  public String value;

  private Logger(String value) {
    // The following code emulates slow initialization.
    try {
        Thread.sleep(1000);
    } catch (InterruptedException ex) {
        ex.printStackTrace();
    }
    this.value = value;
  }

  public static Logger getInstance(String value) {
    if (instance == null) {
        instance = new Logger(value);
    }
    return instance;
  }
}

class Main {
  public static void main(String[] args) {
    System.out.println("If you see the same value, then logger was reused (yay!)" + "\n" +
      "If you see different values, then 2 singletons were created (booo!!)" + "\n\n" +
      "RESULT:" + "\n");
    Logger logger = Logger.getInstance("1st Logger object");
    Logger anotherLogger = Logger.getInstance("2nd Logger object");
    System.out.println(logger.value);
    System.out.println(anotherLogger.value);
  }
}
