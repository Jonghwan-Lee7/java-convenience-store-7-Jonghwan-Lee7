package store.domain.model.impl;


import static java.lang.Math.min;
import static store.exception.ErrorMessages.MORE_THAN_TOTAL_STOCK;

import java.util.ArrayList;
import java.util.List;
import store.domain.model.Product;

public class StoreProduct implements Product {

    private static final int OUT_OF_STOCK = 0;
    private static final String NO_STOCK = "재고 없음";
    private static final String STOCK_FORMAT = "%d개";
    private static final String PROMOTION_STOCK_FORMAT = "- %s %,d원 %s %s\n";
    private static final String NORMAL_STOCK_FORMAT = "- %s %,d원 %s";
    private static final String INITIAL_STATE = "";



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
        if (this.normalStock == OUT_OF_STOCK){
            this.normalStock = stock;
        }

        if (this.promotionStock == OUT_OF_STOCK){
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
        String productStatus = INITIAL_STATE;
        if (this.promotionName != null){
            productStatus = addPromotionProductStatus(productName);
        }

        String normalStock = parseStock(this.normalStock);
        productStatus += String.format(NORMAL_STOCK_FORMAT, productName, this.price, normalStock);

        return productStatus;
    }

    @Override
    public List<Integer> getPurchaseDetails(int purchaseQuantity){
        validatePurchase(purchaseQuantity);
        List<Integer> details = new ArrayList<>();

        int promotionPurchase = getPromotionPurchaseCount(purchaseQuantity);
        details.add(promotionPurchase);
        details.add(this.price);

        return details;
    }


    @Override
    public String getPromotionName(){
        return promotionName;
    }

    @Override
    public boolean hasEnoughPromotionStock(int promotionQuantity){
        if (promotionQuantity > this.promotionStock){
            return false;
        }
        return true;
    }


    private void validatePurchase(int purchaseQuantity){
        if (purchaseQuantity > normalStock + promotionStock){
            throw new IllegalArgumentException(MORE_THAN_TOTAL_STOCK.getErrorMessage());
        }
    }

    private int getPromotionPurchaseCount(int purchaseQuantity){
        return  min(purchaseQuantity, promotionStock);
    }


    private String parseStock(int stock){
        if (stock != OUT_OF_STOCK){
            return String.format(STOCK_FORMAT, stock);
        }

        return NO_STOCK;
    }

    private String addPromotionProductStatus(String productName){
        String promotionStock = parseStock(this.promotionStock);

        return String.format(PROMOTION_STOCK_FORMAT, productName, this.price, promotionStock, this.promotionName);
    }

}
