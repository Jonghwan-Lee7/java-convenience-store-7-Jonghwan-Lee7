package store.domain.processDiscount;

public interface Membership {
    int getDiscountAmount(int totalPurchaseAmount, int promotionAppliedAmount);
}
