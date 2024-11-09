package store.domain.storeOpen;

public interface Promotions {
    Promotion getPromotion(String promotionId);
    void add(String promotionId, Promotion promotion);
}
