package store.domain.impl;


import store.domain.Product;

public class StoreProduct implements Product {
    // 이름 저장하지 말고 Inventory에서 Set의 키값으로 쓰자.
    // 그리고 Promotion Inventory를 따로 구현하자.

    private final int price;
    private int stock;
    private int promotionStock;
    private final StorePromotion promotion;

    private StoreProduct( int price, int stock, int promotionStock, StorePromotion promotion) {
        this.price = price;
        this.promotionStock = promotionStock;
        this.stock = stock;
        this.promotion = promotion;
    }

    public static StoreProduct create(int price, int stock, int promotionStock, StorePromotion promotion) {
        return new StoreProduct( price, stock, promotionStock, promotion);
    }

    public void updateStocks(int salesCount){
        stock -= salesCount;
    }

    public void addStock(int stock){
        if(this.stock == 0){
            this.stock = stock;
        }
    }

    public void addPromotionStock(int promotionStock){
        if(this.promotionStock == 0){
            this.promotionStock = promotionStock;
        }
    }
}
