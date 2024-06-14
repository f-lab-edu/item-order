package fsm;

import constant.Constant;
import input.InputValue;

public class ItemState implements State<OrderContext> {

    @Override
    public void handleInput(OrderContext context) {
        String itemId = InputValue.getCommand(Constant.ITEM);
        if (itemId.equals(" ")) {
            context.setFinished(true);
            return;
        } else {
            context.setItemId(itemId);
            context.transitionState();
        }
    }
}
