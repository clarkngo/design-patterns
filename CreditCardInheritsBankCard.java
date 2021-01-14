class BankCard {
  String cardNumber;
  String nameOfCardholder;
  Date expirationDate;

  validateTransaction() {}
  chargeTransaction() {}
  creditCardPaymentToCard() {}
  printMonthlyStatement() {}
}

class CreditCard extends BankCard {
  int minimumAmountDue;

  printPurchaseBreakdown () {}
}

class Main {
  public static void main(String[] args) {
    System.out.println("Hello world!");
  }
}
