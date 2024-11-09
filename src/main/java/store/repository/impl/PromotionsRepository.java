package store.repository.impl;

import store.domain.Promotions;
import store.repository.SingleRepository;

public class PromotionsRepository implements SingleRepository {
    //이거 스태틱으로 바꿔야 하나? 집가서 코드 다시 보자.
    private Promotions promotions;

    public void save(Promotions promotions) {
        this.promotions = promotions;
    }

    public Promotions get() {
        return promotions;
    }
}
