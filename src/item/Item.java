package item;

import exception.SoldOutException;

public class Item {

    private String id;
    private String name;
    private int price;
    private int stockCount;



    public Item() {}

    public Item(String id, String name, int price, int stockCount) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stockCount = stockCount;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public void setStockCount(int stockCount) {
        this.stockCount = stockCount;
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
