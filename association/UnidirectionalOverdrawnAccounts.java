// Java program to illustrate the
// concept of Association
import java.io.*;

// class bank
class BankAccount
{
	private String owner;
	BankAccount(String o) {
		this.owner = o;
	}
	public String getBankAccountOwner() {
		return this.owner;
	}
}

// employee class
class OverdrawnAccountsReport {
	private String quarterAndYearGenerated;
	// employee name
	OverdrawnAccountsReport(String s) {
		this.quarterAndYearGenerated = s;
	}

	public String getOverdrawnAccountsReport() {
		return this.quarterAndYearGenerated;
	}
}
// Association between both the
// classes in main method
class Association
{
	public static void main (String[] args)
	{
		BankAccount bnkAcct = new BankAccount("Clark");
		OverdrawnAccountsReport oaReport = new OverdrawnAccountsReport("4th quarter of 2020");

		System.out.println(oaReport.getOverdrawnAccountsReport() +
			" overdrawn report is for " + bnkAcct.getBankAccountOwner());
	}
}
