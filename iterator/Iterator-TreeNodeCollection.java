/**
Design Pattern - Iterator

All classes and interfaces are in a single file for quick copy-paste and test run of the code.

You may represent the classes and interfaces in separate Java files:

iterators
|_ TreeIterator.java
|_ DepthFirstIterator.java
|_ BreadthFirstIterator.java

tree_node_collection
|_ TreeNodeCollection.java

Main.java
**/

import java.util.ArrayList;
import java.util.List;

/*********
iterators 
**********/
interface TreeIterator {
  boolean hasNext();
  int getNext();
}

class DepthFirstIterator implements TreeIterator {
  List<Integer> arr = new ArrayList<>();
  int pointer = 0;
  
  public DepthFirstIterator(TreeNodeCollection root) {
    this.arr = inorderTraversal(root, this.arr);
  }
  
  private List<Integer> inorderTraversal(TreeNodeCollection start, List<Integer> traversal) {
    if (start != null) {
      traversal = inorderTraversal(start.left, traversal);
      traversal.add(start.val);
      traversal = inorderTraversal(start.right, traversal);
    }
    return traversal;
  }
  
  @Override
  public int getNext() {
    int val;
    val = this.arr.get(pointer);
    pointer++;
    return val;
  }
  
  @Override
  public boolean hasNext() {
    if (pointer >= this.arr.size()) {
      return false;
    }
    return true;
  }
}

class BreadthFirstIterator implements TreeIterator {
  List<Integer> arr = new ArrayList<>();
  int pointer = 0;
  
  public BreadthFirstIterator(TreeNodeCollection root) {
    helper(root);
  }
  
  public void helper(TreeNodeCollection node) {
    arr.add(node.val);
    if (node.left != null) 
      helper(node.left);
    if (node.right != null)
      helper(node.right);
  }

  @Override
  public int getNext() {
    int val;
    val = this.arr.get(pointer);
    pointer++;
    return val;
  }
  
  @Override
  public boolean hasNext() {
    if (pointer >= this.arr.size()) {
      return false;
    }
    return true;
  }  
}

/*********
tree_node_collection
**********/ 
class TreeNodeCollection {
  int val;
  TreeNodeCollection left;
  TreeNodeCollection right;
  TreeNodeCollection() {}
  TreeNodeCollection(int val) { this.val = val; }
  TreeNodeCollection(int val, TreeNodeCollection left, TreeNodeCollection right) {
      this.val = val;
      this.left = left;
      this.right = right;
  }

  TreeIterator getDepthIterator() {
    return new DepthFirstIterator(this);
  }

  TreeIterator getBreadthIterator() {
    return new BreadthFirstIterator(this);
  }
}

// Client code
class Main {
  public static void main(String[] args) {
    /* Binary Tree
               3
             /   \
            9     20
                 /  \
                15   7
    */
    TreeNodeCollection BT = new TreeNodeCollection(3);
    BT.left =  new TreeNodeCollection(9);
    BT.right =  new TreeNodeCollection(20);
    BT.right.left =  new TreeNodeCollection(15);
    BT.right.right =  new TreeNodeCollection(7);
    
    TreeIterator DFIT = BT.getDepthIterator();
    System.out.println("DepthFirstIterator: ");
    System.out.println("getNext(): " + DFIT.getNext());
    System.out.println("getNext(): " + DFIT.getNext());
    System.out.println("getNext(): " + DFIT.getNext());
    System.out.println("getNext(): " + DFIT.getNext());
    System.out.println("getNext(): " + DFIT.getNext());

    TreeIterator BFIT = BT.getBreadthIterator();
    System.out.println("BreadthFirstIterator: ");
    System.out.println("getNext(): " + BFIT.getNext());
    System.out.println("getNext(): " + BFIT.getNext());
    System.out.println("getNext(): " + BFIT.getNext());
    System.out.println("getNext(): " + BFIT.getNext());
    System.out.println("getNext(): " + BFIT.getNext());
  }
}
