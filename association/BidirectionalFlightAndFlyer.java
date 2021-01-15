// Java program to illustrate the
// concept of Bidirectional Association
import java.util.Date;

class Flight {
	private int flightNumber;
  private Date departureTime;
	Flight(int fn, Date dt) {
		this.flightNumber = fn;
    this.departureTime = dt;
	}
	public Date getDepartureTime() {
		return this.departureTime;
	}
}

class FrequentFlyer {
	private int frequentFlyerNumber;
	private String customerName;
	FrequentFlyer(int ffn, String cn) {
		this.frequentFlyerNumber = ffn;
		this.customerName = cn;
	}
  public String getCustomerName() {
    return this.customerName;
  }
}

class MileageCredit {
  private int bonusMiles;
  MileageCredit(int bm) {
    this.bonusMiles = bm;
  }
  public int getBonusMiles() {
    return this.bonusMiles;
  }
}

class Association {
	public static void main (String[] args) {
    // Date(int year, int month, int date, int hrs, int min, int sec)
    Date dpt = new Date(2021, 1, 14, 6, 30, 5);
		Flight flgt = new Flight(4884, dpt);
		FrequentFlyer ff = new FrequentFlyer(544, "Clark");
    MileageCredit mc = new MileageCredit(10);
		System.out.println("The flight for passenger "
      + ff.getCustomerName() + " is departing at "
      +  flgt.getDepartureTime() +
      ".\nYou have earned: " +  mc.getBonusMiles() + " miles.");
	}
}
