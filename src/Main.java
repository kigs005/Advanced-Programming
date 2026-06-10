import Lecture1_adt.*; // Import all classes from Lecture1_adt package to be used in this client code
import Lecture4_interfaces_abstract_classes.*; // Import all classes from Lecture4_interfaces_abstract_classes package
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
/*
* Client Code for accessing the Lecture1_adt.TransactionInterface.java module
 */
public class Main {

    public static void testTransaction1() {
        Calendar d1 = new GregorianCalendar(); // d1 is an Object [Objects are Reference types]
        Lecture1_adt.Transaction1 t1 = new Lecture1_adt.Transaction1(1000, d1); // amount and d1 are arguments

        System.out.println(t1.toString());
        System.out.println("Lecture1_adt.TransactionInterface Amount: \t " + t1.amount);
        System.out.println("Lecture1_adt.TransactionInterface Date: \t " + t1.date);

        // Please note that the Client Codes can access the data in the class directly through the dot operator
        // This kind of exposure is a threat to both the Representation Independence and Preservation of Invariants
    }


    /** @return a transaction of same amount as t, one month later
     * This is a PRODUCER of the class Lecture1_adt.Transaction2
    * This code will help demonstrate the design exposures still present in Transaction2 class
     * */

    public static Transaction2 makeNextPayment(Transaction2 t) {
        Calendar d =  t.getDate();
        d.add(Calendar.MONTH, 1);
        return new Transaction2(t.getAmount(), d);
    }

    /*
    Testing Transaction2 class
     */
    public static void testTransaction2() {

        Calendar d1 = new GregorianCalendar();

        Lecture1_adt.Transaction2 t = new Lecture1_adt.Transaction2(1000, d1);

        Lecture1_adt.Transaction2 modified_t = makeNextPayment(t);

        System.out.println("\n\nState of the Object T1 After Client Code Tried to Change the Amount");
        System.out.println("Lecture1_adt.TransactionInterface Amount: \t "+modified_t.getAmount());
        System.out.println("Lecture1_adt.TransactionInterface Date: \t "+modified_t.getDate().getTime());

        System.out.println("\n\nHow does T2 Look Like?????");
        System.out.println("Lecture1_adt.TransactionInterface Amount: \t "+modified_t.getAmount());
        System.out.println("Lecture1_adt.TransactionInterface Date: \t "+modified_t.getDate().getTime());

        /* Please note that Although we have solved the problem of Transaction1
        * And client code can no longer use the dot (.) operator to directly access the data
        * There is still some exposure especially if we pass an object of a previous Transaction2 to create a new Transaction2 object
         */

    }


    /** @return a list of 12 monthly payments of identical amounts
    * This code will help demonstrate the design exposures still present in Transaction3 class
     * */
    public static List<Transaction3> makeYearOfPayments (int amount) {

        List<Transaction3> listOfTransaction3s = new ArrayList<>();
        Calendar date = new GregorianCalendar(2024, Calendar.JANUARY, 3);


        for (int i = 0; i < 12; i++) {
            listOfTransaction3s.add(new Transaction3(amount, date));
            date.add(Calendar.MONTH, 1);
        }
        return listOfTransaction3s;
    }

    /*
    Testing Transaction4 class
     */
    public static void testTransaction3() {

        List<Transaction3> allPaymentsIn2024 = makeYearOfPayments(1000);

        // Display all the 12 Transactions
        for (Transaction3 transact : allPaymentsIn2024) {
            System.out.println("\n\n  ::::::::::::::::::::::::::::::::::::::::::::\n");
            System.out.println("Lecture1_adt.TransactionInterface Amount: \t "+transact.getAmount());
            System.out.println("Lecture1_adt.TransactionInterface Date: \t "+transact.getDate().getTime());
        }

        /* Please Check all the 12 transactions displayed and hwo their dates look like
         * Note that Although Transaction3 class resolves to an extent the exposure in Transaction2 class
         * There is still some exposure especially if we pass an object of a previous Transaction3 to create a
         * new Transaction3 object
         */
    }


    /** @return a list of 12 monthly payments of identical amounts
     * This code Show that by judicious copying and defensive programming we eliminate the exposure in Transaction3
     * As defined in the constructor of Transaction4 class
     * */

    public static List<Transaction4> makeYearOfPaymentsFinal (int amount) {

        List<Transaction4> listOfTransaction4s = new ArrayList<>();
        Calendar date = new GregorianCalendar(2024, Calendar.JANUARY, 3);


        for (int i = 0; i < 12; i++) {
            listOfTransaction4s.add(new Transaction4(amount, date));
            date.add(Calendar.MONTH, 1);
        }
        return listOfTransaction4s;
    }

    /*
    Testing Transaction3 class
     */
    public static void testTransaction4() {

        /*
         * Call the function to make all the Twelve transaction in a year of our business
         */

        List<Transaction4> transactionsIn2024 = makeYearOfPaymentsFinal(1200);

        // Display all the 12 transactions
        for (Transaction4 transact : transactionsIn2024) {
            System.out.println("\n\n  ::::::::::::::::::::::::::::::::::::::::::::\n");
            System.out.println("Lecture1_adt.TransactionInterface Amount: \t "+transact.getAmount());
            System.out.println("Lecture1_adt.TransactionInterface Date: \t "+transact.getDate().getTime());
        }

        // Please Take a look at all the 12 transaction now and compare with the outputs of the Transaction3 class
    }


    public static void testLecture4Transactions() {

        // 1. Create a bank account with 1000.0 balance
        BankAccount account = new BankAccount(1000.0);
        System.out.println("Initial Bank Account Balance: $" + account.getBalance());

        // 2. Test BaseTransaction (Concrete Class)
        Calendar d1 = new GregorianCalendar();
        BaseTransaction baseTx = new BaseTransaction(200.0, d1);
        System.out.println("\n--- 2.1 BaseTransaction Details ---");
        baseTx.printTransactionDetails();
        try {
            System.out.println("Applying BaseTransaction:");
            baseTx.apply(account);
        } catch (InsufficientFundsException e) {
            System.out.println("Caught Exception: " + e.getMessage());
        }
        System.out.println("Account Balance after BaseTransaction: $" + account.getBalance());

        // 3. Test DepositTransaction
        DepositTransaction depositTx = new DepositTransaction(500.0, d1);
        System.out.println("\n--- 3.1 DepositTransaction Details ---");
        depositTx.printTransactionDetails();
        System.out.println("Applying DepositTransaction:");
        depositTx.apply(account);
        System.out.println("Account Balance after DepositTransaction: $" + account.getBalance());

        // 4. Test WithdrawalTransaction - Normal successful full withdrawal
        WithdrawalTransaction withdrawTx1 = new WithdrawalTransaction(400.0, d1);
        System.out.println("\n--- 4.1 WithdrawalTransaction Details (Full) ---");
        withdrawTx1.printTransactionDetails();
        try {
            System.out.println("Applying WithdrawalTransaction (amount = 400.0):");
            withdrawTx1.apply(account);
        } catch (InsufficientFundsException e) {
            System.out.println("Caught InsufficientFundsException: " + e.getMessage());
        }
        System.out.println("Account Balance after WithdrawalTransaction: $" + account.getBalance());
        withdrawTx1.printTransactionDetails();

        // 5. Test Reversal of WithdrawalTransaction
        System.out.println("\n--- 5.1 Reversal of Withdrawal ---");
        boolean isReversed = withdrawTx1.reverse();
        System.out.println("Reversal successful? " + isReversed);
        System.out.println("Account Balance after Reversal: $" + account.getBalance());
        withdrawTx1.printTransactionDetails();

        // 6. Test WithdrawalTransaction - Insufficient funds (throws Exception)
        WithdrawalTransaction withdrawTx2 = new WithdrawalTransaction(2000.0, d1);
        System.out.println("\n--- 6.1 Withdrawal exceeding balance (throws Exception) ---");
        try {
            System.out.println("Applying WithdrawalTransaction (amount = 2000.0, Balance = 1500.0):");
            withdrawTx2.apply(account);
        } catch (InsufficientFundsException e) {
            System.out.println("Caught Expected Exception: " + e.getMessage());
        }
        System.out.println("Account Balance after failed withdrawal: $" + account.getBalance());

        // 7. Test Overloaded apply() method with partial withdrawal allowed
        System.out.println("\n--- 7.1 Overloaded apply() - Partial Withdrawal Allowed (amount = 2000.0, Balance = 1500.0) ---");
        withdrawTx2.apply(account, true);
        System.out.println("Account Balance after partial withdrawal: $" + account.getBalance());
        withdrawTx2.printTransactionDetails();

        // 8. Test Reversal of Partial Withdrawal
        System.out.println("\n--- 8.1 Reversal of Partial Withdrawal ---");
        boolean isPartialReversed = withdrawTx2.reverse();
        System.out.println("Reversal successful? " + isPartialReversed);
        System.out.println("Account Balance after partial reversal: $" + account.getBalance());
        withdrawTx2.printTransactionDetails();

        // 9. Test Polymorphic execution and Type Casting
        System.out.println("\n--- 9.1 Polymorphic Execution via Superclass Reference ---");
        BaseTransaction polyDeposit = new DepositTransaction(300.0, d1);
        BaseTransaction polyWithdraw = new WithdrawalTransaction(100.0, d1);

        System.out.println("Polymorphic Deposit apply:");
        try {
            polyDeposit.apply(account);
        } catch (InsufficientFundsException e) {
            System.out.println("Exception: " + e.getMessage());
        }
        System.out.println("Balance after polymorphic deposit: $" + account.getBalance());

        System.out.println("Polymorphic Withdrawal apply:");
        try {
            polyWithdraw.apply(account);
        } catch (InsufficientFundsException e) {
            System.out.println("Exception: " + e.getMessage());
        }
        System.out.println("Balance after polymorphic withdrawal: $" + account.getBalance());

        // Test reversing the polymorphic withdrawal by downcasting
        System.out.println("\nDowncasting polyWithdraw to WithdrawalTransaction for reversal:");
        if (polyWithdraw instanceof WithdrawalTransaction downcastedWithdraw) {
            boolean polyReversed = downcastedWithdraw.reverse();
            System.out.println("Polymorphic withdrawal reversal successful? " + polyReversed);
            System.out.println("Final Account Balance: $" + account.getBalance());
        }

        System.out.println("=================================================");
    }

    public static void main(String[] args) {
        // Run testing for Lecture 4 Assignment
        testLecture4Transactions();

        // This is the client code
        // Uncomment the following lines to test the class which you would like to test

        // testTransaction1()
        // testTransaction2()
        // testTransaction3()
        // testTransaction4()
    }
}