package store.domain.impl;


import store.domain.Product;

public class StoreProduct implements Product {
    // 이름 저장하지 말고 Inventory에서 Set의 키값으로 쓰자.
    // 그리고 Promotion Inventory를 따로 구현하자.

    private final int price;
    private int normalStock;
    private int promotionStock;
    private String promotionName;

    private StoreProduct(int price, int normalStock, int promotionStock, String promotionName) {
        this.price = price;
        this.promotionStock = promotionStock;
        this.normalStock = normalStock;
        this.promotionName = promotionName;
    }


    public static StoreProduct create(int price, int stock, int promotionStock, String promotion) {
        return new StoreProduct( price, stock, promotionStock, promotion);
    }

    @Override
    public void updateStocks(int normalSalesCount, int promotionSalesCount){
        normalStock -= normalSalesCount;
        promotionStock -= promotionSalesCount;
    }

    @Override
    public void addStock(int stock){
        if (this.normalStock == 0){
            this.normalStock = stock;
        }

        if (this.promotionStock == 0){
            this.promotionStock = stock;
        }
    }

    @Override
    public void updatePromotion(String promotionName){
        if (this.promotionName == null ){
            this.promotionName = promotionName;
        }
    }

}
