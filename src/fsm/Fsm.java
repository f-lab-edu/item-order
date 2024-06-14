package fsm;

public abstract class Fsm<T extends State> {

    private T state;

    public Fsm() {
    }

    public abstract void transitionState();

    public abstract void handleEvent(String state);

}
