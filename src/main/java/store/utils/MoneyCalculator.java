package store.utils;

import java.util.List;
import store.dto.FinalOrderDTO;
import store.dto.FinalPromotionDTO;

public class MoneyCalculator implements Calculator {

    @Override
    public int calculateTotalPurchaseAmount(List<FinalOrderDTO> finalOrderDTOs){
        return finalOrderDTOs.stream()
                .mapToInt(finalOrderDTO -> (finalOrderDTO.normalStockCount() + finalOrderDTO.promotionStockCount()) * finalOrderDTO.price())
                .sum();
    }

    @Override
    public int calculateTotalPromotionAmount(List<FinalPromotionDTO> finalPromotionDTOs){
        return finalPromotionDTOs.stream()
                .mapToInt(FinalPromotionDTO::discountAmount)
                .sum();
    }


}
