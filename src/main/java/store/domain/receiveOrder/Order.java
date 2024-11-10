package store.domain.receiveOrder;

import store.domain.storeOpen.Inventory;
import store.domain.storeOpen.Promotion;

public interface Order {
    String getPromotionName();
    String getProductName();
    boolean canGetAdditionalOne(Promotion promotion, Inventory inventory);
    int getRegularPriceCount();

    void updateNormalStock(int stockChange);
    void updatePromotionStock(int stockChange);
    void removeUnAppliedStock();
}
