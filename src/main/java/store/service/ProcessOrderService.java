package store.service;

import java.util.List;
import java.util.Map;
import store.dto.InsufficientStockDTO;

public interface ProcessOrderService {
    List<String> getOrdersWithPossibleAddition();
    void applyAdditionDecision(Map<String, String> customerDecisions);

    List<InsufficientStockDTO> getInsufficientPromotionStocks();
    void applyInsufficientPromotionStock(Map<String, String> customerDecisions);
}
