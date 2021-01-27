/**

Design Pattern - Log

You may represent the classes and interfaces in separate Java files:

Log.java
Main.java

**/

final class Log {
    private static Log instance;
    public String value;

    private Log(String value) {
        // The following code emulates slow initialization.
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        this.value = value;
    }

    public static Log getInstance(String value) {
        if (instance == null) {
            instance = new Log(value);
        }
        return instance;
    }
}

class Main {
  public static void main(String[] args) {
    System.out.println("If you see the same value, then log was reused (yay!)" + "\n" +
            "If you see different values, then 2 singletons were created (booo!!)" + "\n\n" +
            "RESULT:" + "\n");
    Log log = Log.getInstance("1st Log object");
    Log anotherLog = Log.getInstance("2nd Log object");
    System.out.println(log.value);
    System.out.println(anotherLog.value);
  }
}
