package store.utils.parser;

public interface SingleParser<Type> {
    Type parse(String rawTarget);
}
