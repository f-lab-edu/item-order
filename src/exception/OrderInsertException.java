package exception;

import state.OrderState;

public class OrderInsertException extends RuntimeException{
    public OrderInsertException(String message) {
        super(message);
    }
}
