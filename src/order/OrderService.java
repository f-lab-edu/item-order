package order;

import exception.SoldOutException;
import item.Item;
import item.ItemService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.Map;

public class OrderService {
    private ItemService itemService;
    private Order order;

    public OrderService(ItemService itemService) {
        this.itemService = itemService;
    }

    public void processOrder() throws IOException, SoldOutException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        this.order = new Order();

        itemService.displayItems();

        while (true) {
            System.out.print("상품번호: ");
            String itemId = br.readLine();

            System.out.print("수량: ");
            String quantityStr = br.readLine();

            if (itemId.equals(" ") || quantityStr.equals(" ")) break;

            validateOrderData(itemId, quantityStr);
        }
        showOrderDetails(itemService.getItemMap());
    }

    private void validateOrderData(String itemId, String quantityStr) throws SoldOutException {
        Item item = itemService.getItem(itemId);
        if (item == null) {
            System.out.println("상품번호를 확인하세요.");
            return;
        }

        int quantity = 0;
        try {
            quantity = Integer.parseInt(quantityStr);
            if (quantity <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            System.out.println("수량을 확인하세요.");
            return;
        }
        addItems(item, quantity);
    }

    private void addItems(Item item, int quantity) throws SoldOutException {
        boolean hasStock = item.decreaseStock(quantity);
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

    public void showOrderDetails(Map<String, Item> itemMap) {
        Map<String, Integer> orderItems = order.getOrderItems();
        if (orderItems.isEmpty()) return;

        DecimalFormat df = new DecimalFormat("###,###");
        System.out.println("주문내역: ");
        System.out.println("------------------------------");
        for (Map.Entry<String, Integer> data : orderItems.entrySet()) {
            System.out.println(itemMap.get(data.getKey()).getName() + " - " + data.getValue() + "개");
        }

        System.out.println("------------------------------");
        System.out.println("주문금액: " + df.format(order.getOrderPrice()) + "");
        if (order.getOrderPrice() < 50000) {
            System.out.println("배송비: " + df.format(Order.DELIVERY_FEE) + "");
        }

        System.out.println("------------------------------");
        System.out.println("지불금액: " + df.format(order.getTotalPrice()) + "원");
    }

}
