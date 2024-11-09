package store.domain;

public interface Product {

    void updateStocks(int normalSalesCount, int promotionSalesCount);

    void addStock(int stock);

    void updatePromotion(String promotionName);
}
