package store.repository.impl;

import java.util.Optional;
import store.domain.storeOpen.Promotions;
import store.repository.SingleRepository;

public class PromotionsRepository implements SingleRepository<Promotions> {
    private Promotions promotions;

    @Override
    public Promotions save(Promotions promotions) {
        this.promotions = promotions;
        return this.promotions;
    }

    @Override
    public Optional<Promotions> get() {
        return Optional.ofNullable(promotions);
    }
}
