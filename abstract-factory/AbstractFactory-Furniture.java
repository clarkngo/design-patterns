/**
You may represent the class/classes and interfaces in separate folders:

chairs
|_ Chair.java - interface Chair, class VictorianChair, class ModernChair

sofas
|_ Sofa.java - interface Sofa, class VictorianSofa, class ModernSofa

factories
|_ FurnitureFactory.java - interface FurnitureFactory
|_ VictorianFurnitureFactory.java - class VictorianFurnitureFactory
|_ ModernFurnitureFactory.java - class ModernFurnitureFactory

app
|_ Application.java - class Application

Main.java - class Main
**/

/**
 * FIRST PRODUCT HIERARCHY
 * Abstract Factory assumes that you have several families of products,
 * structured into separate class hierarchies (Chair). All products of
 * the same family have the common interface.
 *
 * This is the common interface for chairs family.
 */
interface Chair {
  public boolean hasLegs();
  public boolean sitOn();
  public void build();
}

/**
 * All products families have the same varieties (chair/sofa).
 *
 * This is a Victorian variant of a chair.
 */
class VictorianChair implements Chair {
  String legs;
  String seatCushion;

  VictorianChair() {
    this.legs = (this.hasLegs()) ? "with legs" : "no legs";
    this.seatCushion = (this.sitOn()) ? "cushion" : "no cushion";
  }

  @Override
  public boolean hasLegs() {
    return true;
  }

  @Override
  public boolean sitOn() {
    return true;
  }

  @Override
  public void build() {
    System.out.println("Built Victorian Chair " + this.legs + " and " + this.seatCushion);
  }
}

/**
 * All products families have the same varieties (chair/sofa).
 *
 * This is a Modern variant of a chair.
 */
class ModernChair implements Chair {
  String legs;
  String seatCushion;

  ModernChair() {
    this.legs = (this.hasLegs()) ? "with legs" : "no legs";
    this.seatCushion = (this.sitOn()) ? "cushion" : "no cushion";
  }

  @Override
  public boolean hasLegs() {
    return false;
  }

  @Override
  public boolean sitOn() {
    return false;
  }

  @Override
  public void build() {
    System.out.println("Built Modern Chair " + this.legs + " and " + this.seatCushion);
  }
}

/**
 * SECOND PRODUCT HIERARCHY
 * Abstract Factory assumes that you have several families of products,
 * structured into separate class hierarchies (Sofa). All products of
 * the same family have the common interface.
 *
 * This is the common interface for sofas family.
 */
interface Sofa {
  public boolean hasLegs();
  public boolean sitOn();
  public void build();
}

/**
 * All products families have the same varieties (chair/sofa).
 *
 * This is a Victorian variant of a sofa.
 */
class VictorianSofa implements Sofa {
  String legs;
  String seatCushion;

  VictorianSofa() {
    this.legs = (this.hasLegs()) ? "with legs" : "no legs";
    this.seatCushion = (this.sitOn()) ? "cushion" : "no cushion";
  }

  @Override
  public boolean hasLegs() {
    return true;
  }

  @Override
  public boolean sitOn() {
    return true;
  }

  @Override
  public void build() {
    System.out.println("Built Victorian Sofa " + this.legs + " and " + this.seatCushion);
  }
}

/**
 * All products families have the same varieties (chair/sofa).
 *
 * This is a Modern variant of a sofa.
 */
class ModernSofa implements Sofa {
  String legs;
  String seatCushion;
  ModernSofa() {
    this.legs = (this.hasLegs()) ? "with legs" : "no legs";
    this.seatCushion = (this.sitOn()) ? "cushion" : "no cushion";
  }

  @Override
  public boolean hasLegs() {
    return false;
  }

  @Override
  public boolean sitOn() {
    return false;
  }

  @Override
  public void build() {
    System.out.println("Built Modern Sofa " + this.legs + " and " + this.seatCushion);
  }
}

/**
 * Abstract factory knows about all (abstract) product types.
 */
interface FurnitureFactory {
  Chair createChair();
  Sofa createSofa();
}

/**
 * Each concrete factory extends basic factory and responsible for creating
 * products of a single variety.
 */

// Concrete Factory - Victorian Furniture
class VictorianFurnitureFactory implements FurnitureFactory {
  public Chair createChair() {
    return new VictorianChair();
  }
  public Sofa createSofa() {
    return new VictorianSofa();
  }
}

// Concrete Factory - Modern Furniture
class ModernFurnitureFactory implements FurnitureFactory {
  public Chair createChair() {
    return new ModernChair();
  }
  public Sofa createSofa() {
    return new ModernSofa();
  }
}

/**
 * Factory users don't care which concrete factory they use since they work with
 * factories and products through abstract interfaces.
 */
class Application {
  private Chair chair;
  private Sofa sofa;
  public Application(FurnitureFactory factory) {
    chair = factory.createChair();
    sofa = factory.createSofa();
  }

  public void buildSet() {
    chair.build();
    sofa.build();
  }
}

/**
 * Main class. Everything comes together here.
 */
class Main {
  /**
    * Application picks the factory type and creates it in run time (usually at
    * initialization stage), depending on the configuration or environment
    * variables.
    */
  private static Application configureApplication(String type) {
    Application app;
    FurnitureFactory factory;
    switch(type) {
      case "victorian" :
        factory = new VictorianFurnitureFactory();
        app = new Application(factory);
        break;
      case "modern" :
        factory = new ModernFurnitureFactory();
        app = new Application(factory);
        break;
      default :
        throw new IllegalArgumentException( "Invalid furniture type: " + type);
    }
    return app;
  }

  public static void main(String[] args) {
    String furnitureType = "victorian"; // change this to "victorian" or "modern"
    Application app = configureApplication(furnitureType);
    System.out.println("Furniture Type is: " + furnitureType);
    app.buildSet();
  }
}
