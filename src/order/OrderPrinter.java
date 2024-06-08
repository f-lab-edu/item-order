package order;

import item.ItemService;

import java.text.DecimalFormat;
import java.util.List;

public class OrderPrinter {
    private ItemService itemService;
    private Order order;

    public OrderPrinter(ItemService itemService, Order order) {
        this.itemService = itemService;
        this.order = order;
    }

    public void showOrderDetails() {
        if (order == null) return;

        System.out.println("주문내역: ");
        System.out.println("------------------------------");
        printOrderItems(order.getOrderItems());
        
        System.out.println("------------------------------");
        printOrderAmount();
        printDeliveryFee();

        System.out.println("------------------------------");
        printTotalAmount();
    }

    private void printOrderItems(List<OrderItem> orderList) {
        for ( OrderItem order : orderList) {
            System.out.println(order.getItemNm()+ " - " + order.getOrderCount());
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
