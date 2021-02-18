/**
Design Pattern - State
All classes and interfaces are in a single file for quick copy-paste and test run of the code.

You may represent the classes and interfaces in separate Java files:

package
|_ Package.java

states
|_ PackageState.java
|_ OrderedState.java
|_ DeliveredState.java
|_ ReceivedState.java

Main.java

Reference: https://www.baeldung.com/java-state-design-pattern
**/

// Define our context - Package
// it contains a reference for managing the state, 
// notice previousState(), nextState() and 
// printStatus() methods where we delegate the job 
// to the state object. The states will be linked 
// to each other and every state will set another one 
// based on this reference passed to both methods.
class Package {

private PackageState state = new OrderedState();

  public PackageState getState() {
    return state;
  }

  public void setState(PackageState state) {
    this.state = state;
  }

  public void previousState() {
    state.prev(this);
  }

  public void nextState() {
    state.next(this);
  }

  public void printStatus() {
    state.printStatus();
  }
}
  
interface PackageState {
	void next(Package pkg);
	void prev(Package pkg);
	void printStatus();
}
  
// concrete state class
class OrderedState implements PackageState {

	@Override
	public void next(Package pkg) {
		pkg.setState(new DeliveredState());
	}

	@Override
	public void prev(Package pkg) {
		System.out.println("The package is in its root state.");
	}

	@Override
	public void printStatus() {
		System.out.println("Package ordered, not delivered to the office yet.");
	}
}
  
// concrete state class
class DeliveredState implements PackageState {

	@Override
	public void next(Package pkg) {
		pkg.setState(new ReceivedState());
	}

	@Override
	public void prev(Package pkg) {
		pkg.setState(new OrderedState());
	}

	@Override
	public void printStatus() {
		System.out.println("Package delivered to post office, not received yet.");
	}
}

// concrete state class
class ReceivedState implements PackageState {

	@Override
	public void next(Package pkg) {
		System.out.println("This package is already received by a client.");
	}

	@Override
	public void prev(Package pkg) {
		pkg.setState(new DeliveredState());
	}

	@Override
	public void printStatus() {
		System.out.println("Package was received by client.");
	}  
}

// The client will interact with the Package class, 
// yet he won't have to deal with setting the states, 
// all the client has to do is go to the next or 
// previous state.
// Also, the implementation of printStatus() method 
// changes its implementation at runtime
class Main {
	public static void main(String[] args) {
		Package pkg = new Package();
		pkg.printStatus();

		pkg.nextState();
		pkg.printStatus();

		pkg.nextState();
		pkg.printStatus();

		pkg.nextState();
		pkg.printStatus();
	}
}
  