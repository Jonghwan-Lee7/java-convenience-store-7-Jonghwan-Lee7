package store.domain.builder;


public interface TwoInputsBuilder <TargetType,ParameterType>{
    TargetType build(String first, ParameterType second);
}
