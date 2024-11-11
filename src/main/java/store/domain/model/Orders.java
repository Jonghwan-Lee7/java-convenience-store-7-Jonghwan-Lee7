package store.domain.model;

import java.util.List;
import java.util.Map;
import java.util.Set;
import store.dto.FinalOrderDTO;
import store.dto.FinalPromotionDTO;
import store.dto.InsufficientStockDTO;

public interface Orders {
    List<String> getOrdersWithAdditionalOffer(Promotions promotions, Inventory inventory);
    List<InsufficientStockDTO> getInsufficientPromotionStocks(Promotions promotions, Set<String> decisionExceptions);

    void applyAdditionDecision(Map<String, String> customerDecisions);
    void applyInsufficientPromotionStock(Map<String, String> customerDecisions);

    List<FinalOrderDTO> getFinalOrderDTOs();
    List<FinalPromotionDTO> getFinalPromotionDTOs();
}
