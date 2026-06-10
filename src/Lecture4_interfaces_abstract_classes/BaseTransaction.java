package Lecture4_interfaces_abstract_classes;

import java.util.Calendar;

public class BaseTransaction implements TransactionInterface {
    private final double amount;
    private final Calendar date;
    private final String transactionID;

    /**
     * Lecture1_adt.TransactionInterface Constructor
     * @param amount in an integer
     * @param date: Not null, and must be a Calendar object
     * @return void
     * Instialises the field, attributes of a transaction
     * Creates a object of this
     */
    public BaseTransaction(double amount, Calendar date)  {
        if (date == null) {
            throw new IllegalArgumentException("date cannot be null");
        }
        this.amount = amount;
        this.date = (Calendar) date.clone();
        int uniq = (int)(Math.random()*10000);
        transactionID = date.toString()+uniq;
    }

    /**
     * getAmount()
     * @return integer
     */
    @Override
    public double getAmount() {
        return amount; // Because we are dealing with Value types we need not worry about what we return
    }

    /**
     * getDate()
     * @return Calendar Object
     */
    @Override
    public Calendar getDate() {
        return (Calendar) date.clone(); // Defensive copying or Judicious Copying
    }

    // Method to get a unique identifier for the transaction
    @Override
    public String getTransactionID(){
        return  transactionID;
    }
    // Method to print a transaction receipt or details
    public void printTransactionDetails() {
        System.out.println("--- Base Transaction Receipt ---");
        System.out.println("Transaction ID: " + getTransactionID());
        System.out.println("Date: " + getDate().getTime());
        System.out.println("Amount: $" + getAmount());
        System.out.println("Type: BaseTransaction");
        System.out.println("--------------------------------");
    }

    public void apply(BankAccount ba) throws InsufficientFundsException {
        if (ba == null) {
            throw new IllegalArgumentException("BankAccount cannot be null.");
        }
        System.out.println("BaseTransaction apply is a no-op.");
    }
}
