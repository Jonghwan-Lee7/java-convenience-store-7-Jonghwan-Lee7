package store.domain.processOrder;

public interface Membership {
    int getDiscountAmount(int totalPurchaseAmount, int promotionAppliedAmount);
}
