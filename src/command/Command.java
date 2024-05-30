package command;

import item.Item;

import java.util.Map;

public interface Command {

    boolean input(String command);

    boolean execute(Map<String, Item> itemMap);
}
