/**
Design Pattern - Composite
All classes and interfaces are in a single file for
quick copy-paste and test run of the code.

You may represent the classes and interfaces in separate Java files:

departments
|_ Department.java
|_ FinancialDepartment.java
|_ HeadDepartment.java
|_ SalesDepartment.java

Main.java

Source: https://www.baeldung.com/java-composite-pattern
**/

import java.util.ArrayList;
import java.util.List;

interface Department {
  void printDepartmentName();
}

// This class implement the printDepartmentName()
// method from the base component, where
// they print the class names for each of them.

// Also, as a leaf class, it doesn't
// contain other Department objects.
class FinancialDepartment implements Department {

  private Integer id;
  private String name;

  public FinancialDepartment(Integer id, String name) {
    this.id = id;
    this.name = name;
  }

  public void printDepartmentName() {
    System.out.println(getClass().getSimpleName());
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}

// This class implement the printDepartmentName()
// method from the base component,  where
// they print the class names for each of them.

// Also, as a leaf class, it doesn't
// contain other Department objects.
class SalesDepartment implements Department {

  private Integer id;
  private String name;

  public SalesDepartment(Integer id, String name) {
    this.id = id;
    this.name = name;
  }

  public void printDepartmentName() {
    System.out.println(getClass().getSimpleName());
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}

// This is a composite class as it holds a
// collection of Department components,
// as well as methods for adding and
// removing elements from the list.

// The composite printDepartmentName() method
// is implemented by iterating over the list
// of leaf elements and invoking the
// appropriate method for each one.
class HeadDepartment implements Department {
  private Integer id;
  private String name;

  private List<Department> childDepartments;

  public HeadDepartment(Integer id, String name) {
    this.id = id;
    this.name = name;
    this.childDepartments = new ArrayList<Department>();
  }

  public void printDepartmentName() {
    childDepartments.forEach(Department::printDepartmentName);
  }

  public void addDepartMent(Department department) {
    childDepartments.add(department);
  }

  public void removeDepartment(Department department) {
    childDepartments.remove(department);
  }
}

class Main {
  public static void main(String[] args) {
    Department salesDepartment = new SalesDepartment(1, "Sales department");
    Department financialDepartment = new FinancialDepartment(2, "Financial department");

    HeadDepartment headDepartment = new HeadDepartment(3, "Head department");

    headDepartment.addDepartMent(salesDepartment);
    headDepartment.addDepartMent(financialDepartment);

    headDepartment.printDepartmentName();
  }
}
