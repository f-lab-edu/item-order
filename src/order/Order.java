package order;

import java.util.ArrayList;
import java.util.List;

public class Order {
    public static final int DELIVERY_FEE = 2500;
    public static final int STANDARD_PRICE = 50000;

    private String orderId = null;
    private int orderPrice = 0;
    private int totalPrice= 0;
    private List<OrderItem> orderItems = new ArrayList<>();

    // getter & setter
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getOrderPrice() {
        return orderItems.stream()
                .map(o -> o.getPrice() * o.getOrderCount())
                .reduce(0, (a, b) -> a + b);
    }

    public void setOrderPrice(int orderPrice) {
        this.orderPrice = orderPrice;
    }

    public int getTotalPrice() {
        int orderPrice = getOrderPrice();
        return orderPrice < STANDARD_PRICE ? orderPrice + DELIVERY_FEE : orderPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }
}
