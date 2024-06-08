package command;

import order.OrderService;

public class OrderCommand implements Command {

    @Override
    public boolean input(String command) {
        return command.equalsIgnoreCase("o") || command.equals("order");
    }

    @Override
    public boolean execute() {
        OrderService orderService = new OrderService();
        try {
            orderService.processOrder();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
