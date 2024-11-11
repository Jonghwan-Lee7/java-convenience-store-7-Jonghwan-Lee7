package store.domain.model;

import java.util.List;

public interface Product {

    void updateStocks(int normalSalesCount, int promotionSalesCount);
    void addStock(int stock);
    void updatePromotion(String promotionName);

    String toFormattedString(String productName);
    List<Integer> getPurchaseDetails(int purchaseQuantity);
    String getPromotionName();

    boolean hasEnoughPromotionStock(int promotionQuantity);
}
