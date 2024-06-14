package order;

import exception.OrderInsertException;
import exception.SoldOutException;
import exception.StockUpdateException;
import item.Item;
import item.ItemDao;
import item.ItemService;
import fsm.OrderContext;
import validation.ItemValidator;
import validation.QuantityValidator;
import validation.Validator;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class OrderService {
    private ItemService itemService;
    private OrderPrinter orderPrinter;

    private Order order;
    private OrderDao orderDao;
    private ItemDao itemDao;

    public static final Lock lock = new ReentrantLock();

    public OrderService() {
        this.itemService = new ItemService();
        this.order = new Order();
        this.orderDao = new OrderDao();
        this.itemDao = new ItemDao();
    }

    public void processOrder() {
        itemService.displayItems();
        String orderId = getItemAndQuantity();
        if (!orderId.isEmpty()) {
            List<OrderItem> orderItems = orderDao.showOrderList(orderId);

            if (!orderItems.isEmpty()) {
                order.setOrderItems(orderItems);
                orderPrinter = new OrderPrinter(itemService, order);
                orderPrinter.showOrderDetails();
            }
        }
    }

    private String getItemAndQuantity() {
        // 주문 synchronized
        OrderContext orderContext = new OrderContext();

        String orderId = generateOrderId();
        order.setOrderId(orderId);

        while (!orderContext.isFinished()) {
            // 상태 입력 받기
            orderContext.processOrder();

            if (orderContext.isFinished()) break;

            String itemId = orderContext.getItemId();
            String quantityStr = orderContext.getQuantity();

            // 상품번호, 수량 validation
            validate(itemId, quantityStr);

            // 주문 상품 추가
            Item item = itemService.getItem(itemId);
            int quantity = Integer.parseInt(quantityStr);
            addItems(item, quantity);
        }

        return orderId;
    }

    // 주문 ID 생성
    private String generateOrderId() {
        return String.valueOf(System.currentTimeMillis());
    }
    
    // 상품 번호, 수량 validate
    private boolean validate(String itemId, String quantityStr) {
        Validator itemValid = new ItemValidator();
        Validator quantityValid = new QuantityValidator();

        itemValid.validate(itemId);
        quantityValid.validate(quantityStr);

        return true;
    }

    // 상품 추가
    public void addItems(Item item, int quantity) {
        try {
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
        } catch (SoldOutException | OrderInsertException | StockUpdateException e) {
            System.out.println("주문 처리 중 오류가 발생했습니다. : " + e.getMessage());
        }
    }

    // 재고 수량 확인
    private int checkStock(Item item, int quantity) {
        int stockCount = itemDao.getStockCount(item.getId());

        if (quantity > stockCount || stockCount <= 0) {
            throw new SoldOutException("재고가 부족합니다. : " + item.getId() + " (" + stockCount + ")");
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
