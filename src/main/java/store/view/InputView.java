package store.view;

import store.dto.InsufficientStockDTO;

public interface InputView {
    String readOrder();
    String readChoiceAboutFreeAddition(String productName);
    String readChoiceAboutNoPromotion(InsufficientStockDTO stockDTO);
}
