package store.domain.model;

import java.util.List;
import java.util.Set;
import store.dto.FinalOrderDTO;
import store.dto.FormattedStockDTO;

public interface Inventory {
    List<FormattedStockDTO> toDTOs();
    List<Integer> getPurchaseDetails(String productName, int quantity);
    String getPromotionName(String productName);
    boolean hasEnoughPromotionStock(String productName, int promotionQuantity);
    void updateStocks(List<FinalOrderDTO> finalOrderDTOS);
    void validateProductPromotion(Set<String> promotionNames);
}
