class Wheel {
  int size;
  String material;
  Wheel(int s, String m) {
      this.size=s;
      this.material=m;
  }
}

class Seat {
  int number;
  String material;
  Seat(int n, String m) {
      this.number=n;
      this.material=m;
  }
}

class Engine {
  String type;
  Engine(String t) {
      this.type=t;
  }
}

class Car {
  int yearModel;
  String make;
  //Creating HAS-A relationship with Wheel, Seat, and Engine classes
  Wheel carWheel;
  Seat carSeat;
  Engine carEngine;
  Car(int year, String m, Wheel wl, Seat st, Engine en) {
      this.yearModel=year;
      this.make=m;
      this.carWheel=wl;
      this.carSeat=st;
      this.carEngine=en;
  }
}

class Main {
  public static void main(String args[]) {
      Wheel wl = new Wheel(17, "Chrome");
      Seat st = new Seat(4, "Leather");
      Engine en = new Engine("V Type");
      Car obj = new Car(2021, "Tesla", wl, st, en);
      System.out.println(obj.yearModel);
      System.out.println(obj.make);
      System.out.println(obj.carWheel.size);
      System.out.println(obj.carWheel.material);
      System.out.println(obj.carSeat.number);
      System.out.println(obj.carEngine.type);
  }
}
