package fsm;

import constant.Constant;
import input.InputValue;

public class QuantityState implements State<OrderContext>{

    @Override
    public void handleInput(OrderContext context) {
        String quantity = InputValue.getCommand(Constant.QUANTITY);
        if (quantity.equals(" ")) {
            context.setFinished(true);
            return;
        } else {
            context.setQuantity(quantity);
        }
    }
}
