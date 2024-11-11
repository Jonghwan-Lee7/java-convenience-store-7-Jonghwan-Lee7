package store.domain.model;

public interface Promotions {
    Promotion getPromotion(String promotionId);
    void add(String promotionId, Promotion promotion);
}
