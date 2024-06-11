package order;

import constant.Constant;
import exception.OrderInsertException;
import exception.SoldOutException;
import exception.StockUpdateException;
import item.Item;
import item.ItemDao;
import item.ItemService;
import state.ItemState;
import state.OrderState;
import validation.ItemValidate;
import validation.QuantityValidate;
import validation.Validation;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class OrderService {
    private ItemService itemService;
    private OrderPrinter orderPrinter;
    private Order order = new Order();
    private OrderDao orderDao = new OrderDao();
    private ItemDao itemDao = new ItemDao();
    public static final Lock lock = new ReentrantLock();

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

    public void addItems(Item item, int quantity) {
        if (item == null || quantity == 0) {
            throw new IllegalArgumentException("item or quantity is null");
        }

        // Lock 이용
        int update = 0;
        try {
            lock.lock();
            // 재고 수량 확인
            int stockCount = checkStock(item, quantity);

            // 주문 삽입
            int affected = insertOrder(item, quantity);

            // 재고 update
            if (affected >= 1) {
                update = updateStock(item, quantity, stockCount);
            }
        } finally {
            lock.unlock();
        }

        /* synchroinzed 사용
        synchronized (this) {
            int stockCount = checkStock(item, quantity);

            // 주문 삽입
            int affected = insertOrder(item, quantity);

            // 재고 update
            if (affected >= 1) {
                update = updateStock(item, quantity, stockCount);
            }
        }
        */

        if (update > 0) {
            calcPrice(item, quantity);
        }
    }

    // 재고 수량 확인
    private int checkStock(Item item, int quantity) {
        int stockCount = itemDao.getStockCount(item.getId());

        if (quantity > stockCount || stockCount <= 0) {
            try {
                throw new SoldOutException("재고가 부족합니다. : " + item.getId() + " (" + stockCount + ")");
            } catch (SoldOutException e) {
                e.printStackTrace();
            }
        }
        return stockCount;
    }

    // 주문
    private int insertOrder(Item item, int quantity) {
        int affected = 0;
        affected = orderDao.insertOrder(item.getId(), quantity, order);

        if (affected < 1) {
            throw new OrderInsertException("주문에 실패했습니다.");
        }
        return affected;
    }

    // 재고 update
    private int updateStock(Item item, int quantity, int stockCount) {
        int affected = 0;
        int remain = stockCount - quantity;
        affected += itemDao.updateStockCount(item.getId(), remain);

        if (affected < 1) {
            throw new StockUpdateException("재고 업데이트에 실패했습니다.");
        }
        return affected;
    }

    public void calcPrice(Item item, int quantity) {
        int orderPrice = order.getOrderPrice() + item.getPrice() * quantity;
        int totalPrice = (orderPrice < Order.STANDARD_PRICE) ? orderPrice + Order.DELIVERY_FEE : orderPrice;
        order.setOrderPrice(orderPrice);
        order.setTotalPrice(totalPrice);
    }
}
