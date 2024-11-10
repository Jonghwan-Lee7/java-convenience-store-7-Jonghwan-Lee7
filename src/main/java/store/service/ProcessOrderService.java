package store.service;

import java.util.List;
import store.dto.InsufficientStockDTO;

public interface ProcessOrderService {
    List<String> getOrdersWithPossibleAddition();
    void applyCustomerDecisionToOrders();
    List<InsufficientStockDTO> getInsufficientPromotionStocks();
}
