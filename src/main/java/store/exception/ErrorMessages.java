package store.exception;

public enum ErrorMessages {
    ERROR_HEADER("[Error] "),
    NO_SAVED_INVENTORY("저장된 인벤토리가 없습니다,"),
    NO_SAVED_PROMOTIONS("저장된 프로모션들이 없습니다."),
    NO_SAVED_ORDERS("저장된 주문목록이 없습니다."),
    INVALID_FORMAT("올바르지 않은 형식으로 입력했습니다. 다시 입력해주세요"),
    INVALID_PRODUCT_NAME("존재하지 않는 상품입니다. 다시 입력해주세요"),
    WRONG_INPUT("잘못된 입력입니다. 다시 입력해주세요"),
    MORE_THAN_TOTAL_STOCK("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해주세요.");

    private final String message;
    ErrorMessages(String message) {
        this.message = message;
    }
    public String getErrorMessage() {
        return ERROR_HEADER.getMessage() + message;
    }

    public String getMessage(){
        return message;
    }

}
