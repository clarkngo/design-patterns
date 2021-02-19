/**
Design Pattern - Decorator
All classes and interfaces are in a single file for
quick copy-paste and test run of the code.

You may represent the classes and interfaces in separate Java files:

christmas_tree
|_ ChristmasTree.java
|_ ChristmasTreeImpl.java

decorators
|_ TreeDecorator.java
|_ Garland.java
|_ BubbleLights.java

Main.java

Source: https://www.baeldung.com/java-decorator-pattern
**/

interface ChristmasTree {
  String decorate();
}

class ChristmasTreeImpl implements ChristmasTree {

  @Override
  public String decorate() {
    return "Christmas tree";
  }
}

// decorator will implement the ChristmasTree interface
// as well as hold the same object.
// The implemented method from the same interface will
// simply call the decorate() method from our interface:
abstract class TreeDecorator implements ChristmasTree {
  private ChristmasTree tree;

  public TreeDecorator(ChristmasTree tree) {
    this.tree = tree;
  }
  // standard constructors
  @Override
  public String decorate() {
    return tree.decorate();
  }
}

// decorators will extend our abstract TreeDecorator class
// and will modify its decorate() method
class BubbleLights extends TreeDecorator {

  public BubbleLights(ChristmasTree tree) {
    super(tree);
  }

  public String decorate() {
    return super.decorate() + decorateWithBubbleLights();
  }

  private String decorateWithBubbleLights() {
    return " with Bubble Lights";
  }
}

// decorators will extend our abstract TreeDecorator class
// and will modify its decorate() method
class Garland extends TreeDecorator {

  public Garland(ChristmasTree tree) {
    super(tree);
  }

  public String decorate() {
    return super.decorate() + decorateWithGarland();
  }

  private String decorateWithGarland() {
    return " with Garland";
  }
}

class Main {
public static void main(String[] args) {
  ChristmasTree tree1 = new Garland(new ChristmasTreeImpl());
  System.out.println(tree1.decorate());

  ChristmasTree tree2 = new BubbleLights(
    new Garland(new Garland(new ChristmasTreeImpl())));
    System.out.println(tree2.decorate());
  }
}
