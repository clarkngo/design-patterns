class Wheel {
  int size;
  String material;
  Wheel(int s, String m) {
      this.size=s;
      this.material=m;
  }
}

class Car {
  int yearModel;
  String make;
  //Creating HAS-A relationship with Wheel class
  Wheel carWheel;
  Car(int year, String m, Wheel wl) {
      this.yearModel=year;
      this.make=m;
      this.carWheel=wl;
  }
}

class Main {
  public static void main(String args[]) {
      Wheel wl = new Wheel(17, "Chrome");
      Car obj = new Car(2021, "Tesla", wl);       System.out.println(obj.yearModel);
      System.out.println(obj.make);
      System.out.println(obj.carWheel.size);
      System.out.println(obj.carWheel.material);
  }
}
