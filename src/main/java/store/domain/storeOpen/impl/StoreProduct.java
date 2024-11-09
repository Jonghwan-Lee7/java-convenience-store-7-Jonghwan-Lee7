package store.domain.storeOpen.impl;


import store.domain.storeOpen.Product;

public class StoreProduct implements Product {

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


    public static StoreProduct of(int price, int stock, int promotionStock, String promotion) {
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

    @Override
    public String toFormattedString(String productName) {
        String productStatus = "";
        if (this.promotionName != null){
            productStatus = addPromotionProductStatus(productName);
        }

        String normalStock = parseStock(this.normalStock);
        productStatus += String.format("- %s %,d원 %s", productName, this.price, normalStock);

        return productStatus;
    }

    private String parseStock(int stock){
        if (stock != 0){
            return String.format("%d개", stock);
        }

        return "재고 없음";
    }

    private String addPromotionProductStatus(String productName){
        String promotionStock = parseStock(this.promotionStock);

        return String.format("- %s %,d원 %s %s\n", productName, this.price, promotionStock, this.promotionName);
    }

}
