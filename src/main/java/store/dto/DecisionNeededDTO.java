package store.dto;

import java.util.List;

public record DecisionNeededDTO(List<String> OrdersWithPossibleAddition,List<InsufficientStockDTO> insufficientPromotionStocks) {
}
