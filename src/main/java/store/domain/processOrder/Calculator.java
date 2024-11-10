package store.domain.processOrder;

import java.util.List;
import store.dto.FinalOrderDTO;
import store.dto.FinalPromotionDTO;

public interface Calculator {
    int calculateTotalPurchaseAmount(List<FinalOrderDTO> finalOrderDTOs);
    int calculateTotalPromotionAmount(List<FinalPromotionDTO> finalPromotionDTOs);
    int calculatePromotionAppliedAmount(List<FinalPromotionDTO> finalPromotionDTOs);
    int calculateAmountToPay(int totalPurchaseAmount, int totalPromotionAmount, int membershipDiscountAmount);
    int calculateTotalCounts(List<FinalOrderDTO> finalOrderDTOs);
}
