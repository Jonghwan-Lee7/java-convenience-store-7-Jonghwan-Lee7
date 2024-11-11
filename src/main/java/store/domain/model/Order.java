package store.domain.model;

import store.dto.FinalOrderDTO;
import store.dto.FinalPromotionDTO;

public interface Order {
    String getPromotionName();
    String getProductName();
    boolean canGetAdditionalOne(Promotion promotion, Inventory inventory);
    int getRegularPriceCount();

    void updatePromotionStock(int stockChange);
    void removeUnAppliedStock();

    FinalOrderDTO getFinalOrderDTO();
    FinalPromotionDTO getFinalPromotionDTO();
}
