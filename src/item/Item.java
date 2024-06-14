package item;

public class Item {

    private String id;
    private String name;
    private int price;
    private int stockCount;

    public Item() {}

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
}
