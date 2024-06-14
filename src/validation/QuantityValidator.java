package validation;

public class QuantityValidator implements Validator {

    @Override
    public boolean validate(String value) {
        try {
            int quantity = Integer.parseInt(value);
            if (quantity <= 0) {
                throw new IllegalArgumentException("주문 수량은 1 이상이어야 합니다");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("올바르지 않은 수량입니다.");
        }
        return true;
    }
}
