package store.domain.storeOpen;

public interface Promotion {
    int getApplicableItemCount (int purchaseCount);
    int getFreeItemCount (int purchaseCount);
}
