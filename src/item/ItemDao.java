package item;

import jdbc.JdbcManager;

import java.sql.*;

public class ItemDao {

    public ItemDao() {}

    public Item selectOne(String id) {
        String sql = "SELECT * FROM item WHERE item_id = ?";
        Item item = null;
        try (Connection conn = JdbcManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                item = new Item();
                item.setId(rs.getString("item_id"));
                item.setName(rs.getString("item_nm"));
                item.setPrice(rs.getInt("price"));
                item.setStockCount(rs.getInt("stock_count"));
            }

        } catch (SQLException e) {
            System.out.println("select 메서드 예외 발생");
            e.printStackTrace();
        }
        return item;
    }

    public void selectAll() {
        String sql = "SELECT * FROM item";
        try (Connection conn = JdbcManager.getConnection();
             PreparedStatement psmt = conn.prepareStatement(sql)) {

            ResultSet rs = psmt.executeQuery();

            System.out.println("상품번호\t 상품명\t\t\t\t 판매가격\t 재고수");

            while (rs.next()) {
                System.out.println(rs.getString("item_id") + "\t" + rs.getString("item_nm") + "\t" +
                        rs.getString("price") + "\t" + rs.getString("stock_count"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 재고 수량
    public int getStockCount(String id) {
        String sql = "SELECT stock_Count FROM item WHERE item_id = ?";
        try (Connection conn = JdbcManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("stock_count");
            }

        } catch (SQLException e) {
            System.out.println("getStockCount 예외 발생");
            e.printStackTrace();
        }
        return -1;
    }

    // 재고 update
    public int updateStockCount(String id, int remainStock) {
        String sql = "UPDATE item SET stock_count = ? where item_id = ?";
        try (Connection conn = JdbcManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, remainStock);
            pstmt.setString(2, id);

            pstmt.executeUpdate();

            return 1;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
