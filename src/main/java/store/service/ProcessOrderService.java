package store.service;

import java.util.List;
import store.dto.PromotionStockInsufficientDTO;

public interface ProcessOrderService {
    List<String> getOrdersWithPossibleAddition();
    void applyCustomerDecisionToOrders();
    List<PromotionStockInsufficientDTO> getOrdersWithLackPromotionStock();
}
