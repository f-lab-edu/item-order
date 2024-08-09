package fsm;

import constant.Constant;

public class OrderContext extends Fsm<StateHandler> {
    // 오더의 로작
    private String itemId;
    private String quantity;

    public OrderContext() {
        state = new ItemState();
        isFinished = false;
    }

//    @Override
//    public void transitionState() { // 상태 전이 로직
//        // instanceof
//        // 값으로 비료 -> enum
//        if (!isFinished) {
//            if (state instanceof ItemState)
//                state = new QuantityState();
//            else if (state instanceof QuantityState)
//                state = new ItemState();
//            state.handleInput(this);
//        }
//    }

    @Override
    public void handleEvent(String event) { // 이벤트 처리 로직
        if (!isFinished) {
            //state.handleInput(this);
            StateHandler newState = state.handlerEvent(event);
            if (state != newState) {
                state = newState;
                state.handleInput(this);
            }

        }
    }

    public void processOrder() {
        handleEvent(State.ORDER_ITEM.name());
    }

    /* getter & setter */

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
