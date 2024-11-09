package store.domain;

public interface SingleBuilder<Type,InputType> {
    Type build(InputType rawInput);
}
