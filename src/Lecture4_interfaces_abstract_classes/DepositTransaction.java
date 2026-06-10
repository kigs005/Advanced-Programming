package Lecture4_interfaces_abstract_classes;

import java.util.Calendar;

public class DepositTransaction extends BaseTransaction {
    public DepositTransaction(double amount, Calendar date) {
        super(amount, date);
    }

    @Override
    public void printTransactionDetails() {

        System.out.println("\nDeposit Transaction");
        System.out.println("Transaction ID: "
                + getTransactionID());

        System.out.println("Amount: "
                + getAmount());

        System.out.println("Date: "
                + getDate().getTime());
    }

    @Override
    public void apply(BankAccount ba) {
        if (ba == null) {
            throw new IllegalArgumentException("BankAccount cannot be null.");
        }
        double curr_balance = ba.getBalance();
        double new_balance = curr_balance + getAmount();
        ba.setBalance(new_balance);
    }
}