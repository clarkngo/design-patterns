// factory - base creator
abstract class Logistics {
  public void planDelivery() {
    Transport t = createTransport();
    t.deliver();
  }
  public abstract Transport createTransport();
}

// factory - concrete creator
// RoadLogistics will produce Trucks
class RoadLogistics extends Logistics {

  @Override
  public Transport createTransport() {
    return new Truck();
  }
}

// factory - concrete creator
// SeaLogistics will produce Ships
class SeaLogistics extends Logistics {

  @Override
  public Transport createTransport() {
    return new Ship();
  }
}

// Common product interface for all transportation
interface Transport {
  public void deliver(); // interface method (does not have a body)
}

// Concrete product - Ship "implements" the Transport interface
class Ship implements Transport {
  public void deliver() {
    System.out.println("Deliver by sea in a container");
  }
}

// Concrete product - Truck "implements" the Transport interface
class Truck implements Transport {
  public void deliver() {
    System.out.println("Deliver by land in a box");
  }
}

// Client code
class Main {
  private static Logistics logistics;
  public static void main(String[] args) {
    configure("land");    // change this to "land" or
    runBusinessLogic();
  }
  static void configure(String mode) {
      switch(mode) {
         case "land" :
            logistics = new RoadLogistics();
            break;
         case "sea" :
            logistics = new SeaLogistics();
            break;
         default :
            System.out.println("Invalid mode");
      }
  }
  static void runBusinessLogic() {
    logistics.planDelivery();
  }
}
