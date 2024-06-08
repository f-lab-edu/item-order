package validation;

public class QuantityValidate implements Validation{
    @Override
    public boolean validate(String value) {
        int quantity = 0;
        quantity = Integer.parseInt(value);
        try {
            if (quantity <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            System.out.println("수량을 확인하세요. " + e.getMessage());
        }
        return true;
    }
}
