package fsm;

import constant.Constant;
import input.InputValue;

public class QuantityState implements StateHandler {

    @Override
    public StateHandler handlerEvent(String event) {



        return new QuantityState();
    }

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
