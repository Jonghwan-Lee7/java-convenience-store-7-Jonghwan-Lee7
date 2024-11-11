package store.constants;

public enum Answer {
    YES("Y"),
    NO("N");

    private final String message;

    Answer(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}
