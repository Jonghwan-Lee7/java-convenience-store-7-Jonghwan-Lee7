package store.domain.repository.impl;

import java.util.Optional;
import store.domain.model.Promotions;
import store.domain.repository.SingleRepository;

public class PromotionsRepository implements SingleRepository<Promotions> {
    private Promotions promotions;

    @Override
    public void save(Promotions promotions) {
        this.promotions = promotions;
    }

    @Override
    public Optional<Promotions> get() {
        return Optional.ofNullable(promotions);
    }
}
