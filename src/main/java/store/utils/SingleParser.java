package store.utils;

public interface SingleParser<Type> {
    Type parse(String rawTarget);
}
