package jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class BaseDao<T> {

    public <T> T execute(String sql,
                              ResultSetHandler<T> handler,
                              Object...params) {
        try (Connection conn = JdbcManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (int i=0; i<params.length; i++) {
                pstmt.setObject(i+1, params[i]);
            }

            if (sql.trim().startsWith("SELECT")) {
                ResultSet rs = pstmt.executeQuery();
                return handler.handle(rs);

            } else {
                int count = pstmt.executeUpdate();
                return (T) Integer.valueOf(count);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
