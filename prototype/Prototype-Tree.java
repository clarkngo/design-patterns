import java.util.Objects;
import java.util.List;
import java.util.ArrayList;

/**

Design Pattern - Prototype

You may represent the class/classes and interfaces in separate folders:

trees
|_ Tree.java
|_ PineTree.java
|_ PlasticTree.java

Main.java

**/

/**
 * Common tree interface
 */
abstract class Tree {

  public int mass;
  public int height;
  public String color;
  public Tree() {}

  public Tree(Tree target) {
    if (target != null) {
      this.mass = target.mass;
      this.height = target.height;
      this.color = target.color;
    }
  }

  public abstract Tree clone();

  @Override
  public boolean equals(Object object2) {
    if (!(object2 instanceof Tree)) return false;
    Tree tree2 = (Tree) object2;
    return tree2.mass == mass && tree2.height == height && Objects.equals(tree2.color, color);
  }
}

/**
 * Simple tree
 */
class PineTree extends Tree {

  public int oxygenProduction;
  public PineTree() {}

  public PineTree(PineTree target) {
    super(target);
    if (target != null) {
      this.oxygenProduction = target.oxygenProduction;
    }
  }

  @Override
  public Tree clone() {
    return new PineTree(this);
  }

  @Override
  public boolean equals(Object object2) {
    if (!(object2 instanceof PineTree) || !super.equals(object2)) return false;
    PineTree tree2 = (PineTree) object2;
    return tree2.oxygenProduction == oxygenProduction;
  }
}

/**
 * Another tree
 */
class PlasticTree extends Tree {

  public int carbonFootprint;
  public PlasticTree() {}

  public PlasticTree(PlasticTree target) {
    super(target);
    if (target != null) {
      this.carbonFootprint = target.carbonFootprint;
    }
  }

  @Override 
  public Tree clone() {
    return new PlasticTree(this);
  }

  @Override
  public boolean equals(Object object2) {
    if (!(object2 instanceof PlasticTree) || !super.equals(object2)) return false;
    PlasticTree tree2 = (PlasticTree) object2;
    return tree2.carbonFootprint == carbonFootprint;
  }
}

/**
 * Main class. Everything comes together here.
 */
class Main {
 
  public static void main(String[] args) {
    List<Tree> trees = new ArrayList<>();
    List<Tree> treesCopy = new ArrayList<>();

    PineTree pineTree = new PineTree();
    pineTree.mass = 20;
    pineTree.height = 40;
    pineTree.color = "green";
    pineTree.oxygenProduction = 260;
    trees.add(pineTree);

    PineTree anotherPineTree = (PineTree) pineTree.clone();
    trees.add(anotherPineTree);

    PlasticTree plasticTree = new PlasticTree();
    plasticTree.mass = 10;
    plasticTree.height = 5;
    plasticTree.color = "orange";
    plasticTree.carbonFootprint = 80;
    trees.add(plasticTree);

    cloneAndCompare(trees, treesCopy);
  }  

  private static void cloneAndCompare(List<Tree> trees, List<Tree> treesCopy) {
    for (Tree tree: trees) {
      treesCopy.add(tree.clone());
    }

    for (int i = 0; i < trees.size(); i++) {
      if (trees.get(i) != treesCopy.get(i)) {
        System.out.println(i + ": Trees are different objects (nice!)");
        if (trees.get(i).equals(treesCopy.get(i))) {
          System.out.println(i + ": And they are identical (nice!)");
        } else {
          System.out.println(i + ": But they are not identical (too bad!)");
        } 
      } else {
          System.out.print(i + ": Tree objects are the same (too bad!)");
      }
    }
  }
}
