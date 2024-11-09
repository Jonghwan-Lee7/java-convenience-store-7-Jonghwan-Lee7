package store.domain;

public interface Product {
    void updateStocks(int salesCount);
    void addStock(int stock);
}
