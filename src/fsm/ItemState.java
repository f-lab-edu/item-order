package fsm;

import constant.Constant;
import input.InputValue;
import item.Item;

public class ItemState implements StateHandler {

    @Override
    public StateHandler handlerEvent(String event) {

        return new ItemState();
    }

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
