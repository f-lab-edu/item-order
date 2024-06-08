package state;

public class OrderState {
    private State state;
    private boolean isFinished;
    private String itemId;
    private String quantity;

    public OrderState() {
        state = new ItemState();
        isFinished = false;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void processInput(String input) {
        if (!isFinished) {
            state.handleInput(this, input);
        }
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setFinished(boolean isFinished) {
        this.isFinished = isFinished;
    }

    public boolean isFinished() {
        return isFinished;
    }
}
