package store.domain.model;

public interface Promotion {
    boolean isActive();
    int getApplicableItemCount (int purchaseCount);
    int getFreeItemCount (int purchaseCount);
}
