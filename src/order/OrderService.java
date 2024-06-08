package order;

import constant.Constant;
import item.Item;
import item.ItemService;
import state.ItemState;
import state.OrderState;
import validation.ItemValidate;
import validation.QuantityValidate;
import validation.Validation;

import java.util.List;

public class OrderService {
    private ItemService itemService;
    private Order order = new Order();
    private OrderPrinter orderPrinter;
    private OrderDao orderDao = new OrderDao();

    public OrderService() {
        this.itemService = new ItemService();
    }

    public void processOrder() {
        itemService.displayItems();
        String orderId = getItemAndQuantity();
        if (!orderId.isEmpty()) {
            List<OrderItem> orderItems = orderDao.showOrderList(orderId);
            order.setOrderItems(orderItems);
            orderPrinter = new OrderPrinter(itemService, order);
            orderPrinter.showOrderDetails();
        }
    }

    private String getItemAndQuantity() {
        // 주문 synchronized
        OrderState orderState = new OrderState();

        String orderId = generateOrderId();
        order.setOrderId(orderId);

        while (!orderState.isFinished()) {
            Item item = new Item();
            int quantity = 0;
            orderState.setState(new ItemState());
            orderState.processInput(Constant.ITEM);

            if (orderState.isFinished()) break;
            item = itemService.getItem(orderState.getItemId());
            quantity = Integer.parseInt(orderState.getQuantity());

            // 주문 상품 추가
            addItems(item, quantity);
        }

        return orderId;
    }

    private String generateOrderId() {
        return String.valueOf(System.currentTimeMillis());
    }
    

    private boolean validate(String itemId, String quantity) {
        Validation itemValid = new ItemValidate();
        Validation quantityValid = new QuantityValidate();

        itemValid.validate(itemId);
        quantityValid.validate(quantity);

        return true;
    }

    private void addItems(Item item, int quantity) {
        /* 수량이 있는지 확인
           실제 주문할 때 여러 주문에서 하나만 실패시 나머지도 복구 필요
           전체 주문완료시 재고감소
        */
        orderDao = new OrderDao();
        // 재고 수량 확인
        synchronized (this) {
            int stock = itemService.getStockCount(item.getId());
            if (quantity <= stock && stock > 1) {
                int count = 0;
                count += orderDao.insertOrder(item.getId(), quantity, order);

                if (count >= 1) {
                    int remainStock = stock - quantity;
                    itemService.updateStockCount(item.getId(), remainStock);
                }

            } else {
                System.out.println("재고가 부족합니다. : " + item.getId());
            }
        }
        calcPrice(item, quantity);
    }

    public void calcPrice(Item item, int quantity) {
        int orderPrice = order.getOrderPrice() + item.getPrice() * quantity;
        int totalPrice = (orderPrice < Order.STANDARD_PRICE) ? orderPrice + Order.DELIVERY_FEE : orderPrice;
        order.setOrderPrice(orderPrice);
        order.setTotalPrice(totalPrice);
    }

}
