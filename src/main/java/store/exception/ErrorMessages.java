package store.exception;

public enum ErrorMessages {
    NO_SAVE_INVENTORY("저장된 인벤토리가 없습니다");

    private final String message;
    ErrorMessages(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }

}
