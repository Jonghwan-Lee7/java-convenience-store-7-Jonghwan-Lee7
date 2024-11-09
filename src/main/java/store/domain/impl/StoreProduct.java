package store.domain.impl;


import store.domain.Product;

public class StoreProduct implements Product {

    private final String name;
    private final int price;
    private int stock;
    private final StorePromotion promotion;

    private StoreProduct(String name, int price, int stock, StorePromotion promotion) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.promotion = promotion;
    }

    public static StoreProduct create(String name, int price, int stock, StorePromotion promotion) {
        return new StoreProduct(name, price, stock, promotion);
    }

    public void updateStock(int salesCount){
        stock -= salesCount;
    }
}
