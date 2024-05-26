package order;

import java.util.HashMap;
import java.util.Map;

public class Order {
    public static final int DELIVERY_FEE = 2500;
    public static final int STANDARD_PRICE = 50000;
    private int orderPrice = 0;
    private int totalPrice= 0;
    private Map<String, Integer> orderItems = new HashMap<>();

    public int getOrderPrice() {
        return orderPrice;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public Map<String, Integer> getOrderItems() {
        return orderItems;
    }

    public void setOrderPrice(int orderPrice) {
        this.orderPrice = orderPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void addItems(String id, int quantity) {
        orderItems.put(id, quantity);
    }

}
