package order;

import jdbc.JdbcManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDao {

    public OrderDao() {}

    public String generateOrderId() {
        String sql = "INSERT INTO orders (item_id, stock_count) VALUES (NULL, 0)";
        try (Connection conn = JdbcManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getString(1); // 생성된 order_id 반환
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int insertOrder(String id, int quantity, Order order) {
        String sql = "INSERT INTO orders (order_id, item_id, stock_count) VALUES (?, ?, ?)";
        try (Connection conn = JdbcManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Order 객체에서 필요한 정보를 가져와서 SQL 문에 설정합니다.
            pstmt.setString(1, order.getOrderId());
            pstmt.setString(2, id);
            pstmt.setInt(3, quantity);

            pstmt.executeUpdate();
            return 1;

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
