package item;

public class ItemService {
    private ItemDao itemDao = new ItemDao();

    public ItemService() {
    }

    public void displayItems() {
        itemDao.selectAll();
    }

    public Item getItem(String id) {
        return itemDao.selectOne(id);
    }

    public boolean checkStock(Item item, int quantity) {
        if (item.getStockCount() >= quantity) return true;
        else return false;
    }

    public int getStockCount(String id) {
        return itemDao.getStockCount(id);
    }

    public int updateStockCount(String id, int remainStock) {
        return itemDao.updateStockCount(id, remainStock);
    }
}
