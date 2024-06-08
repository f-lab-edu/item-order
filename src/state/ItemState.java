package state;

import constant.Constant;
import input.InputValue;

public class ItemState implements State {
    @Override
    public void handleInput(OrderState state, String input) {
        String itemId = InputValue.getCommand(input);
        if (itemId.equals(" ")) {
            state.setFinished(true);
            return;
        }

        state.setItemId(itemId);

        state.setState(new QuantityState());
        state.processInput(Constant.QUANTITY);
    }
}
