package store.domain.builder;

import java.util.List;

public interface InputBuilder<TargetType> {
    TargetType build(List<String> rawInput);
}
