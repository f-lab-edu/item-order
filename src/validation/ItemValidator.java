package validation;

import item.Item;
import item.ItemService;

public class ItemValidator implements Validator {

    private ItemService itemService;

    @Override
    public boolean validate(String value) {
        itemService = new ItemService();
        if (value != null ) {
            Item item = itemService.getItem(value);
            if (item == null) {
                throw new IllegalArgumentException("상품 번호를 확인하세요.");
            }
        }
        return true;
    }
}
