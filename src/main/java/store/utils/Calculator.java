package store.utils;

import java.util.List;
import store.dto.FinalOrderDTO;
import store.dto.FinalPromotionDTO;

public interface Calculator {
    int calculateTotalPurchaseAmount(List<FinalOrderDTO> finalOrderDTOs);

    int calculateTotalPromotionAmount(List<FinalPromotionDTO> finalPromotionDTOs);
}
