package store.service;

import java.util.Map;
import store.dto.DecisionNeededDTO;

public interface ProcessOrderService {
    DecisionNeededDTO getOrders();

    void applyAdditionDecision(Map<String, String> customerDecisions);
    void applyInsufficientPromotionStock(Map<String, String> customerDecisions);
}
