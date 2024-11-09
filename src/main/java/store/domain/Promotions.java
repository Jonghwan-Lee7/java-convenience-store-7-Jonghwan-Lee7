package store.domain;

public interface Promotions {
    Promotion getPromotion(String promotionId);
    void add(String promotionId, Promotion promotion);
}
