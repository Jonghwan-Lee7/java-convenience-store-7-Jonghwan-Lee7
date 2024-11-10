package store.domain.storeOpen;

public interface Promotion {
    boolean isActive();
    int getApplicableItemCount (int purchaseCount);
    int getFreeItemCount (int purchaseCount);
    boolean canGetFreeItem(int purchaseCount);
}
