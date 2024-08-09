package fsm;

public interface StateHandler {

    void handleInput(OrderContext context);

    StateHandler handlerEvent(String event);

}