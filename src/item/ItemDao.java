package item;

import jdbc.BaseDao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ItemDao extends BaseDao {

    public Item selectOne(String id) {
        String sql = "SELECT * FROM item WHERE item_id = ?";

        List<String> strlist = new ArrayList<>();
        strlist.stream().map(a -> a + 1).collect(Collectors.toList());
        return (Item) execute(sql, rs -> {
            if (rs.next()) {
                Item item = new Item();
                item.setId(rs.getString("item_id"));
                item.setName(rs.getString("item_nm"));
                item.setPrice(rs.getInt("price"));
                item.setStockCount(rs.getInt("stock_count"));
                return item;
            } else {
                return null;
            }
        }, id);
    }

    public void selectAll() {
        String sql = "SELECT * FROM item";

        List<Item> itemList = (List<Item>) execute(sql, rs -> {
            List<Item> resultList = new ArrayList<>();
            while (rs.next()) {
                Item item = new Item();
                item.setId(rs.getString("item_id"));
                item.setName(rs.getString("item_nm"));
                item.setPrice(rs.getInt("price"));
                item.setStockCount(rs.getInt("stock_count"));
                resultList.add(item);
            }
            return resultList;
        });

        System.out.println("상품번호\t 상품명\t\t\t\t 판매가격\t 재고수");
        for (Item item : itemList) {
            System.out.println(item.getId() + "\t" + item.getName() + "\t" +
                            item.getPrice() + "\t" + item.getStockCount());
        }
    }

    public int getStockCount(String id) {
        String sql = "SELECT stock_count FROM item WHERE item_id = ?";

        int stockCount = (int) execute(sql, rs -> {
            int count = 0;
            if (rs.next()) {
                count = rs.getInt("stock_count");
            }
            return count;
        }, id);

        return stockCount;

    }

    public int updateStockCount(String id, int remainStock) {
        String sql = "UPDATE item SET stock_count = ? where item_id = ?";

        return (int) execute(sql, rs -> {
            return null;
        }, remainStock, id);
    }
}
