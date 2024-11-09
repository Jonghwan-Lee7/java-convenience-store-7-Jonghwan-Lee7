package store.utils;

public interface Parser<Type> {
    Type parse(String rawTarget);
}
