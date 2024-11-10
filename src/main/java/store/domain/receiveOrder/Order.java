package store.domain.receiveOrder;

import store.domain.storeOpen.Inventory;
import store.domain.storeOpen.Promotion;
import store.dto.FinalOrderDTO;
import store.dto.FinalPromotionDTO;

public interface Order {
    String getPromotionName();
    String getProductName();
    boolean canGetAdditionalOne(Promotion promotion, Inventory inventory);
    int getRegularPriceCount();

    void updateNormalStock(int stockChange);
    void updatePromotionStock(int stockChange);
    void removeUnAppliedStock();

    FinalOrderDTO getFinalOrderDTO();
    FinalPromotionDTO getFinalPromotionDTO();
}
