package store.domain;

public interface Builder<Type,InputType> {
    Type build(InputType rawInput);
}
