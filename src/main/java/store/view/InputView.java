package store.view;

public interface InputView {
    String readOrder();
    String readChoiceAboutFreeAddition(String productName);
    String readChoiceAboutNoPromotion(String productName, int quantity);
}
