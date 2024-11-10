package store.domain.storeOpen;

import java.util.List;
import store.dto.FinalOrderDTO;
import store.dto.StockDTO;

public interface Inventory {
    List<StockDTO> toDTOs();
    List<Integer> getPurchaseDetails(String productName, int quantity);
    String getPromotionName(String productName);
    boolean hasEnoughPromotionStock(String productName, int promotionQuantity);

    void updateStocks(List<FinalOrderDTO> finalOrderDTOS);
}
