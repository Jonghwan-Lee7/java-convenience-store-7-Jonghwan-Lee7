package store.domain.impl;

import java.util.HashMap;
import java.util.Map;
import store.domain.Promotion;
import store.domain.Promotions;

public class StorePromotions implements Promotions {
    private final Map<String,Promotion> promotions;

    private StorePromotions() {
        this.promotions = new HashMap<>();
    }

    public static StorePromotions create() {
        return new StorePromotions();
    }

    @Override
    public Promotion getPromotion(String promotionId) {
        return promotions.get(promotionId);
    }

    public void add(String promotionId, Promotion promotion) {
        promotions.put(promotionId, promotion);
    }
}
