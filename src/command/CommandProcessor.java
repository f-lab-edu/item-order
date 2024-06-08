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

    public void run() {
        while(true) {
            String command = InputValue.getCommand("입력(o[order] 주문, q[quit] 종료):");
            try {
                boolean handle = handle(command);
                if(!handle)
                    break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }

        }
    }

    private boolean handle(String command) {
        return commands.stream()
                .filter(o -> o.input(command))
                .findFirst()
                .orElseThrow(() ->
                    new IllegalArgumentException("명령을 잘못입력했습니다.")
                )
                .execute();
    }
}
