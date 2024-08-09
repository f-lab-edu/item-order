package fsm;

import java.util.Map;
import java.util.function.Function;

public abstract class Fsm<T extends StateHandler> {

    protected T state;

    public boolean isFinished = false;

    public static Event event = Event.ITEM_INPUT;

    public static State current = State.ORDER_ITEM;   // 상태 초기값 세팅

    public Map<State, Function<Event, State>> map;

    public void transitionState() {
        while (!isFinished) {
            if(!current.getEnableCommand().containsKey(event)) {
                System.out.println("currentState : " + current);
            } else {
                Function<Event, State> state= current.getEnableCommand().get(event);
                current = state.apply(event);
                handleEvent(current.name());
            }
        }
    };

    public abstract void handleEvent(String state);

}
