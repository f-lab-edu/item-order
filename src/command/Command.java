package command;

public interface Command {

    boolean input(String command);

    boolean execute();
}
