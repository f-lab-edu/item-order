package exception;

public class OrderInsertException extends RuntimeException{
    public OrderInsertException(String message) {
        super(message);
    }
}
