package order;

import item.ItemDao;
import jdbc.JdbcManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDao {
    private ItemDao itemDao = new ItemDao();
    public OrderDao() {}

    // 주문 insert
    public int insertOrder(String itemId, int quantity, Order order) {
        String insertSql = "INSERT INTO orders (order_id, item_id, stock_count) VALUES (?, ?, ?)";
        try (Connection conn = JdbcManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
            conn.createStatement();

            conn.setAutoCommit(false);

            // 주문 insert
            pstmt.setString(1, order.getOrderId());
            pstmt.setString(2, itemId);
            pstmt.setInt(3, quantity);

            int count = pstmt.executeUpdate();
            conn.commit();

            return count;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public List<OrderItem> showOrderList(String orderId) {
        String sql = "SELECT o.item_id, o.stock_count, i.item_nm, i.price " +
                       "FROM orders o " +
                       "JOIN item i " +
                         "ON o.item_id = i.item_id " +
                      "WHERE o.order_id = ?";

        List<OrderItem> orderList = new ArrayList<>();
        try (Connection conn = JdbcManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, orderId);
            ResultSet rs = pstmt.executeQuery();


            while (rs.next()) {
                OrderItem orderItem = new OrderItem();
                orderItem.setItemId(rs.getString("item_id"));
                orderItem.setItemNm(rs.getString("item_nm"));
                orderItem.setPrice(rs.getInt("price"));
                orderItem.setOrderCount(rs.getInt("stock_count"));
                orderList.add(orderItem);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderList;
    }
}
