package command;

public class QuitCommand implements Command {
    @Override
    public boolean input(String command) {
        return command.equals("quit") ||
                command.equalsIgnoreCase("q");
    }

    @Override
    public boolean execute() {
        System.out.println("상품 주문 프로그램을 종료합니다.");
        return false;
    }
}
