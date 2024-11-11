package store.domain.processDiscount.impl;

import java.util.List;
import store.domain.processDiscount.Calculator;
import store.dto.FinalOrderDTO;
import store.dto.FinalPromotionDTO;

public class MoneyCalculator implements Calculator {

    @Override
    public int calculateTotalPurchaseAmount(List<FinalOrderDTO> finalOrderDTOs){
        return finalOrderDTOs.stream()
                .mapToInt(FinalOrderDTO::price)
                .sum();
    }

    @Override
    public int calculateTotalPromotionAmount(List<FinalPromotionDTO> finalPromotionDTOs){
        return finalPromotionDTOs.stream()
                .mapToInt(FinalPromotionDTO::discountAmount)
                .sum();
    }

    @Override
    public int calculatePromotionAppliedAmount(List<FinalPromotionDTO> finalPromotionDTOs){
        return finalPromotionDTOs.stream()
                .mapToInt(eachDTO -> (eachDTO.promotionAppliedCount() * eachDTO.discountAmount() / eachDTO.freeCount()))
                .sum();
    }

    @Override
    public int calculateAmountToPay(int totalPurchaseAmount, int totalPromotionAmount, int membershipDiscountAmount) {
        return totalPurchaseAmount - totalPromotionAmount - membershipDiscountAmount;
    }

    @Override
    public int calculateTotalCounts(List<FinalOrderDTO> finalOrderDTOs) {
        return finalOrderDTOs.stream()
                .mapToInt(finalOrderDTO -> (finalOrderDTO.normalStockCount() + finalOrderDTO.promotionStockCount()))
                .sum();
    }


}
