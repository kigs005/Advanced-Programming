package Lecture4_interfaces_abstract_classes;

import java.util.Calendar;

public class WithdrawalTransaction extends BaseTransaction {
    private BankAccount associatedAccount;
    private boolean isApplied = false;
    private double actualWithdrawnAmount = 0.0;
    private double remainingAmountNotWithdrawn = 0.0;

    public WithdrawalTransaction(double amount, Calendar date) {
        super(amount, requireDate(date));
    }

    private static Calendar requireDate(Calendar date) {
        if (date == null) {
            throw new IllegalArgumentException("date cannot be null");
        }
        return date;
    }

    private boolean checkWithdrawalAmount(double amt) {
        return amt >= 0;
    }

    // Method to reverse the transaction
    public boolean reverse() {
        if (isApplied && associatedAccount != null) {
            double currentBalance = associatedAccount.getBalance();
            associatedAccount.setBalance(currentBalance + actualWithdrawnAmount);
            System.out.println("Withdrawal reversed: Refunded $" + actualWithdrawnAmount + " to the account. New Balance: $" + associatedAccount.getBalance());
            this.isApplied = false;
            this.associatedAccount = null;
            this.actualWithdrawnAmount = 0.0;
            this.remainingAmountNotWithdrawn = 0.0;
            return true;
        }
        System.out.println("Reversal failed: Transaction has not been applied or is already reversed.");
        return false;
    }

    // Method to print a transaction receipt or details
    @Override
    public void printTransactionDetails() {
        System.out.println("--- Withdrawal Transaction Receipt ---");
        System.out.println("Transaction ID: " + getTransactionID());
        System.out.println("Date: " + getDate().getTime());
        System.out.println("Amount: $" + getAmount());
        System.out.println("Actual Withdrawn Amount: $" + actualWithdrawnAmount);
        System.out.println("Remaining Amount Not Withdrawn: $" + remainingAmountNotWithdrawn);
        System.out.println("Applied: " + isApplied);
        System.out.println("Type: Withdrawal (Reversible)");
        System.out.println("--------------------------------------");
    }

    @Override
    public void apply(BankAccount ba) throws InsufficientFundsException {
        if (ba == null) {
            throw new IllegalArgumentException("BankAccount cannot be null.");
        }
        double curr_balance = ba.getBalance();
        if (curr_balance < getAmount()) {
            throw new InsufficientFundsException("Insufficient funds. Account balance: $" + curr_balance + ", Withdrawal amount: $" + getAmount());
        }
        double new_balance = curr_balance - getAmount();
        ba.setBalance(new_balance);

        this.associatedAccount = ba;
        this.isApplied = true;
        this.actualWithdrawnAmount = getAmount();
        this.remainingAmountNotWithdrawn = 0.0;
        System.out.println("Withdrawal applied: Debited $" + getAmount() + ". New Balance: $" + new_balance);
    }

    // Overloaded apply method implementing partial withdrawal exception handling
    public void apply(BankAccount ba, boolean allowPartial) {
        if (ba == null) {
            throw new IllegalArgumentException("BankAccount cannot be null.");
        }
        if (allowPartial) {
            try {
                this.apply(ba);
            } catch (InsufficientFundsException e) {
                double balance = ba.getBalance();
                if (balance > 0) {
                    double amountNotWithdrawn = getAmount() - balance;
                    this.remainingAmountNotWithdrawn = amountNotWithdrawn;
                    this.actualWithdrawnAmount = balance;

                    ba.setBalance(0);
                    this.associatedAccount = ba;
                    this.isApplied = true;
                    System.out.println("Partial withdrawal applied. Withdrew available balance: $" + balance);
                    System.out.println("Amount not withdrawn (record kept): $" + amountNotWithdrawn);
                } else {
                    System.out.println("Withdrawal failed. Balance is not greater than 0 (Balance: $" + balance + ").");
                }
            } finally {
                System.out.println("Partial withdrawal attempt completed.");
            }
        } else {
            try {
                this.apply(ba);
            } catch (InsufficientFundsException e) {
                System.out.println("Withdrawal failed due to insufficient funds: " + e.getMessage());
            }
        }
    }
}

