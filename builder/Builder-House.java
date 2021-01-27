/**

Design Pattern - Builder

You may represent the class/classes and interfaces in separate folders:

builders
|_ Builder.java
|_ HouseBuilder.java
|_ HouseManualBuilder.java

houses
|_ House.java
|_ Manual.java
|_ CarType.java

components
|_ GarageDoor.java

director
|_ Director.java

Main.java

**/

/**
 * Builder interface defines all possible ways to configure a product.
 */
interface Builder {
    void setHouseType(HouseType type);
    void setDoors(int doors);
    void setGarageDoor(GarageDoor garageDoor);
  }
  
  /**
   * Concrete builders implement steps defined in the common interface.
   */
  class HouseBuilder implements Builder {
    private HouseType type;
    private int doors;
    private GarageDoor garageDoor;
  
    @Override
    public void setHouseType(HouseType type) {
      this.type = type;
    }
  
    @Override
    public void setDoors(int doors) {
      this.doors = doors;
    }
  
    @Override
    public void setGarageDoor(GarageDoor garageDoor) {
      this.garageDoor = garageDoor;
    }
  
    public House getResult() {
      return new House(type, doors, garageDoor);
    }
  }
  
  /**
   * Unlike other creational patterns, Builder can construct unrelated products,
   * which don't have the common interface.
   *
   * In this case we build a user manual for a house, using the same steps as we
   * built a house. This allows to produce manuals for specific house models,
   * configured with different features.
   */
  class HouseManualBuilder implements Builder {
    private HouseType type;
    private int doors;
    private GarageDoor garageDoor;
  
    @Override
    public void setHouseType(HouseType type) {
      this.type = type;
    }
  
    @Override
    public void setDoors(int doors) {
      this.doors = doors;
    }
  
    @Override
    public void setGarageDoor(GarageDoor garageDoor) {
      this.garageDoor = garageDoor;
    }
  
    public Manual getResult() {
      return new Manual(type, doors, garageDoor);
    }
  }
  
  class House {
    private final HouseType houseType;
    private final int doors;
    private final GarageDoor garageDoor;
  
    public House(HouseType houseType, int doors, GarageDoor garageDoor) {
      this.houseType = houseType;
      this.doors = doors;
      this.garageDoor = garageDoor;
    }
  
    public HouseType getHouseType() {
      return houseType;
    }
  
    public int getDoors() {
      return doors;
    }
  
    public GarageDoor getGarageDoor() {
      return garageDoor;
    }
  }
  
  class Manual {
    private final HouseType houseType;
    private final int doors;
    private final GarageDoor garageDoor;
  
    public Manual(HouseType houseType, int doors, GarageDoor garageDoor) {
      this.houseType = houseType;
      this.doors = doors;
      this.garageDoor = garageDoor;
    }
  
    public String print() {
      String info = "";
      info += "Type of house: " + houseType + "\n";
      info += "Count of doors: " + doors + "\n";
      info += "GarageDoor: dimesion - " + garageDoor.getWide() + " feet wide and " + garageDoor.getTall() + " feet tall" + "; mileage - " + garageDoor.getMileage() + "\n"; 
      return info;
    }
  }
  
  enum HouseType {
    SIMPLE_HOUSE, TINY_HOME
  }
  
  class GarageDoor {
    private final double wide;
    private final double tall;
    private double mileage;
    private boolean powered;
  
    public GarageDoor(double wide, double tall, double mileage) {
      this.wide = wide;
      this.tall = tall;
      this.mileage = mileage;
    }
  
    public void on() {
      powered = true;
    }
  
    public void off() {
      powered = false;
    }
  
    public boolean isPowered() {
      return powered;
    }
  
    public void operate(double mileage) {
      if (powered) {
        this.mileage += mileage;
        System.out.println("operate() incurred mileage of: " + this.mileage);
      } else {
        System.out.println("Cannot operate(), you must power garage door first!");
      }
    }
  
    public double getWide() {
      return wide;
    }
  
    public double getTall() {
      return tall;
    }
  
    public double getMileage() {
      return mileage;
    }
  }
  
  class Director {
  
    public void constructSimpleHouse(Builder builder) {
      builder.setHouseType(HouseType.SIMPLE_HOUSE);
      builder.setDoors(3);
      builder.setGarageDoor(new GarageDoor(16,7,0));
    }
  
    public void constructTinyHome(Builder builder) {
      builder.setHouseType(HouseType.TINY_HOME);
      builder.setDoors(1);
      builder.setGarageDoor(new GarageDoor(8,7,0));
    }
  }
  
  /**
   * Main class. Everything comes together here.
   */
  class Main {
   
    public static void main(String[] args) {
      Director director = new Director();
  
      // Director gets the concrete builder object from the client
      // (application code). That's because application knows better which
      // builder to use to get a specific product.
      HouseBuilder builder = new HouseBuilder();
      director.constructTinyHome(builder);
  
      // The final product is often retrieved from a builder object, since
      // Director is not aware and not dependent on concrete builders and
      // products.
      House house = builder.getResult();
      System.out.println("House built:\n" + house.getHouseType());
  
      // Using a component of a final product and executing an operation
      System.out.println("Operate garage door before powering:");
      house.getGarageDoor().operate(0.5);
      System.out.println("Operate garage door after powering:");
      house.getGarageDoor().on();
      house.getGarageDoor().operate(0.5);
  
      HouseManualBuilder manualBuilder = new HouseManualBuilder();    
  
      // Director may know several building recipes.
      director.constructTinyHome(manualBuilder);
      Manual houseManual = manualBuilder.getResult();
      System.out.println("\nHouse manual built:\n" + houseManual.print());
    }  
  }
  