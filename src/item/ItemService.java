package item;

import java.util.Map;

public class ItemService {
    private Map<String, Item> itemMap;

    public ItemService(Map<String, Item> itemMap) {
        this.itemMap = itemMap;
    }

    public void displayItems() {
        System.out.println("상품번호\t 상품명\t\t\t\t 판매가격\t 재고수");
        for (Map.Entry<String, Item> item : itemMap.entrySet()) {
            System.out.println(item.getKey() + "\t" + item.getValue().getName() + "\t" + item.getValue().getPrice() + "\t" + item.getValue().getStockCount());
        }
    }

    public Item getItem(String itemId) {
        return itemMap.get(itemId);
    }

    public Map<String, Item> getItemMap() {
        return itemMap;
    }
}
