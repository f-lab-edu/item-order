package command;

import item.Item;
import order.OrderService;

import java.util.Map;

public class OrderCommand implements Command {

    @Override
    public boolean input(String command) {
        return command.equalsIgnoreCase("o") || command.equals("order");
    }

    @Override
    public boolean execute(Map<String, Item> itemMap) {
        OrderService orderService = new OrderService(itemMap);
        try {
            orderService.processOrder();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
