package validation;

import item.Item;
import item.ItemService;

public class ItemValidate implements Validation {
    private ItemService itemService;
    @Override
    public boolean validate(String value) {
        if (value != null) {
            Item item = itemService.getItem(value);
            if (item == null) {
                System.out.println("상품 번호를 확인하세요.");
                throw new IllegalArgumentException("상품 번호를 확인하세요.");
            }
        }
        return true;
    }
}
