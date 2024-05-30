package order;

import exception.SoldOutException;
import input.InputValue;
import item.Item;
import item.ItemService;

import java.util.Map;

public class OrderService {
    private ItemService itemService;
    private Order order = new Order();
    private OrderPrinter orderPrinter;

    public OrderService(Map<String, Item> itemMap) {
        this.itemService = new ItemService(itemMap);
    }

    public void processOrder() {
        // 상품 표시
        itemService.displayItems();
        while (true) {
            boolean addItem = getItemAndQuantity();
            if (addItem == false) break;
        }
        orderPrinter = new OrderPrinter(itemService, order);
        orderPrinter.showOrderDetails();
    }

    private boolean getItemAndQuantity() {
        String itemId = InputValue.getItemId();
        String quantityStr = InputValue.getOrderCount();

        if (itemId.equals(" ") || quantityStr.equals(" ")) return false;

        Item item = validateOrderItem(itemId);
        int quantity = validateQuantity(quantityStr);

        if (item != null && quantity > 0) {
            addItems(item, quantity);
        }
        return true;
    }

    private Item validateOrderItem(String itemName) {
        Item item = itemService.getItem(itemName);
        if (item == null) {
            System.out.println("상품 번호를 확인하세요.");
            throw new IllegalArgumentException("상품 번호를 확인하세요.");
        }
        return item;
    }

    private int validateQuantity(String quantityStr) {
        int quantity = 0;
        try {
            quantity = Integer.parseInt(quantityStr);
            if (quantity <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            System.out.println("수량을 확인하세요.");
        }
        return quantity;
    }

    private void addItems(Item item, int quantity) {
        boolean hasStock = false;
        try {
            hasStock = item.decreaseStock(quantity);
        } catch (SoldOutException e) {
            System.out.println(e.getMessage());
        }
        if (hasStock) {
            order.addItems(item.getId(), quantity);
            calcPrice(item, quantity);
        }
    }

    public void calcPrice(Item item, int quantity) {
        int orderPrice = order.getOrderPrice() + item.getPrice() * quantity;
        int totalPrice = (orderPrice < Order.STANDARD_PRICE) ? orderPrice + Order.DELIVERY_FEE : orderPrice;
        order.setOrderPrice(orderPrice);
        order.setTotalPrice(totalPrice);
    }

}
