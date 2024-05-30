package command;

import input.InputValue;
import item.Item;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CommandProcessor {

    private List<Command> commands;

    public CommandProcessor(Command... commands) {
        this.commands = Arrays.asList(commands);
    }

    public void run(Map<String, Item> itemMap) {
        while(true) {
            String command = InputValue.getCommand();
            try {
                boolean handle = handle(command, itemMap);
                if(!handle)
                    break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }

        }
    }

    private boolean handle(String command, Map<String, Item> itemMap) {
        return commands.stream()
                .filter(o -> o.input(command))
                .findFirst()
                .orElseThrow(() ->
                    new IllegalArgumentException("명령을 잘못입력했습니다.")
                )
                .execute(itemMap);
    }
}
