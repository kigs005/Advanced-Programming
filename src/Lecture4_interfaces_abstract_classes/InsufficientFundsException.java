package Lecture4_interfaces_abstract_classes;

/**
 * Exception thrown when a bank account does not have sufficient funds for a withdrawal.
 */
public class InsufficientFundsException extends Exception {

    /**
     * Constructs a new InsufficientFundsException with a null detail message.
     */
    public InsufficientFundsException() {
        super();
    }

    /**
     * Constructs a new InsufficientFundsException with the specified detail message.
     *
     * @param message the detail message
     */
    public InsufficientFundsException(String message) {
        super(message);
    }
}
