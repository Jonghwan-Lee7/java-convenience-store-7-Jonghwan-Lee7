package store.domain.builder;

public interface SingleBuilder<Type,InputType> {
    Type build(InputType rawInput);
}
