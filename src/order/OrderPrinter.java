package order;

import item.Item;
import item.ItemService;

import java.text.DecimalFormat;
import java.util.Map;

public class OrderPrinter {
    private ItemService itemService;
    private Order order;

    public OrderPrinter(ItemService itemService, Order order) {
        this.itemService = itemService;
        this.order = order;
    }

    public void showOrderDetails() {
        Map<String, Integer> orderItems = order.getOrderItems();
        if (orderItems.isEmpty()) return;

        System.out.println("주문내역: ");
        System.out.println("------------------------------");
        printOrderItems(orderItems);
        
        System.out.println("------------------------------");
        printOrderAmount();
        printDeliveryFee();
        
        System.out.println("------------------------------");
        printTotalAmount();
    }

    private void printOrderItems(Map<String, Integer> orderItems) {
        Map<String, Item> itemMap = itemService.getItemMap();
        for (Map.Entry<String, Integer> data : orderItems.entrySet()) {
            System.out.println(itemMap.get(data.getKey()).getName() + " - " + data.getValue() + "개");
        }
    }

    private void printOrderAmount() {
        DecimalFormat df = new DecimalFormat("###,###");
        System.out.println("주문금액: " + df.format(order.getOrderPrice()) + "원");
    }

    private void printDeliveryFee() {
        DecimalFormat df = new DecimalFormat("###,###");
        if (order.getOrderPrice() < 50000) {
            System.out.println("배송비: " + df.format(Order.DELIVERY_FEE) + "원");
        }
    }

    private void printTotalAmount() {
        DecimalFormat df = new DecimalFormat("###,###");
        System.out.println("지불금액: " + df.format(order.getTotalPrice()) + "원");
    }
}
