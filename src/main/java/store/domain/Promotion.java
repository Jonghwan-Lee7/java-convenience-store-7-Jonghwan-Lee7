package store.domain;

public interface Promotion {
    int getApplicableItemCount (int purchaseCount);
    int getFreeItemCount (int purchaseCount);
}
