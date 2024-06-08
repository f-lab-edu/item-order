package state;

import input.InputValue;

public class QuantityState implements State{
    @Override
    public void handleInput(OrderState state, String input) {
        String quantity = InputValue.getCommand(input);
        if (quantity.equals(" ")) {
            state.setFinished(true);
            return;
        }

        state.setQuantity(quantity);
    }
}
