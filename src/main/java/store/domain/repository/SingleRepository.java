package store.domain.repository;

import java.util.Optional;

public interface SingleRepository <TargetType> {
    void save (TargetType object);

    Optional<TargetType> get();
}
