package item;

import exception.SoldOutException;

public class Item {

    private String id;
    private String name;
    private int price;
    private int stockCount;

    public Item(String id, String name, int price, int stockCount) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stockCount = stockCount;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getStockCount() {
        return stockCount;
    }

    public boolean decreaseStock(int quantity) throws SoldOutException {
        try {
            if (quantity > stockCount) {
                throw new SoldOutException("SoldOutException. 재고가 부족합니다.");
            }
            stockCount -= quantity;
            return true;
        } catch (SoldOutException e) {
            System.out.println(e.getMessage());
            return false;
        }

    }
}
