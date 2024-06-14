package fsm;

public interface State<C> {

    void handleInput(C context);

}