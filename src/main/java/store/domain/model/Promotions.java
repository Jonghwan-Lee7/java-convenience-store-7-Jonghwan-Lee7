package store.domain.model;

import java.util.Set;

public interface Promotions {
    Promotion getPromotion(String promotionId);
    Set<String> getPromotionNames();
    void add(String promotionId, Promotion promotion);
}
