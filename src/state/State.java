package state;

public interface State {
    void handleInput(OrderState state, String input);
}