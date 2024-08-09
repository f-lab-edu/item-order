package order;

import item.ItemDao;
import jdbc.BaseDao;

import java.util.ArrayList;
import java.util.List;

public class OrderDao extends BaseDao {
    private ItemDao itemDao = new ItemDao();
    public OrderDao() {}

    // 주문 insert
    public int insertOrder(String itemId, int quantity, Order order) {
        String sql = "INSERT INTO orders (order_id, item_id, stock_count) " +
                     "VALUES (?, ?, ?)";

        return (int) execute(sql, rs -> {
            return null;
        }, order.getOrderId(), itemId, quantity);
    }


    public List<OrderItem> showOrderList(String orderId) {
        String sql = "SELECT o.item_id, o.stock_count, i.item_nm, i.price " +
                       "FROM orders o " +
                       "JOIN item i " +
                         "ON o.item_id = i.item_id " +
                      "WHERE o.order_id = ?";

        List<OrderItem> orderList = (List<OrderItem>) execute(sql, rs -> {
            List<OrderItem> resultList = new ArrayList<>();
            while (rs.next()) {
                OrderItem orderItem = new OrderItem();
                orderItem.setItemId(rs.getString("item_id"));
                orderItem.setItemNm(rs.getString("item_nm"));
                orderItem.setPrice(rs.getInt("price"));
                orderItem.setOrderCount(rs.getInt("stock_count"));
                resultList.add(orderItem);
            }
            return resultList;
        }, orderId);

        return orderList;
    }
}
