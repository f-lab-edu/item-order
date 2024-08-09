package fsm;

import java.util.Map;
import java.util.function.Function;

public enum State {
    INIT,
    ORDER_ITEM,
    ORDER_QUANTITY,
    EXECUTE,
    EXIT;

    static {
        INIT.setEnableCommand(Map.of(
                Event.ORDER, e -> ORDER_ITEM,
                Event.QUIT, e -> EXIT
        ));
        ORDER_ITEM.setEnableCommand(Map.of(
                Event.ITEM_INPUT, e -> ORDER_QUANTITY,
                Event.FINISH, e -> EXECUTE
        ));
        ORDER_QUANTITY.setEnableCommand(Map.of(
                Event.QUANTITY_INPUT, e -> ORDER_ITEM,
                Event.FINISH, e -> EXECUTE
        ));
        EXECUTE.enableCommand = Map.of(Event.EXECUTE, e -> EXIT);
    }

    State() {}

    public void setEnableCommand(Map<Event, Function<Event, State>> enableCommand) {
        this.enableCommand = enableCommand;
    }

    private Map<Event, Function<Event, State>> enableCommand;

    State (Map<Event, Function<Event, State>> enableCommand){
        this.enableCommand = enableCommand;
    };

    public Map<Event, Function<Event, State>> getEnableCommand() {
        return enableCommand;
    }



}
