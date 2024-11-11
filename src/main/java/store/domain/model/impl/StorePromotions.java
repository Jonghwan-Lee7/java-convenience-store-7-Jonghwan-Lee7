package store.domain.model.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import store.domain.model.Promotion;
import store.domain.model.Promotions;

public class StorePromotions implements Promotions {
    private final Map<String, Promotion> promotions;

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

    @Override
    public Set<String> getPromotionNames(){
        return new HashSet<>(promotions.keySet());
    }

    public void add(String promotionName, Promotion promotion) {
        promotions.put(promotionName, promotion);
    }
}
