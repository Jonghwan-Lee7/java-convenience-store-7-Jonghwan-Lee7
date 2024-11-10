package store.domain.receiveOrder.impl;

import store.domain.receiveOrder.Order;
import store.domain.storeOpen.Inventory;
import store.domain.storeOpen.Promotion;

public class StoreOrder implements Order {
    private final String productName;
    private int normalStock;
    private int promotionStock;
    private int promotionAppliedStock = 0;
    private int totalPrice;
    private int freeStock = 0;
    private final String promotionName;

    private StoreOrder(String productName, int normalStock, int promotionStock, int price, String promotionName) {
        this.productName = productName;
        this.normalStock = normalStock;
        this.promotionStock = promotionStock;
        this.totalPrice = (normalStock + promotionStock) * price;
        this.promotionName = promotionName;
    }


    public static StoreOrder of(String productName, int normalStock, int promotionStock, int price, String promotionName) {
        return new StoreOrder(productName, normalStock, promotionStock, price, promotionName);
    }

    @Override
    public String getPromotionName() {
        return promotionName;
    }

    @Override
    public String getProductName(){
        return productName;
    }


    @Override
    public boolean canGetAdditionalOne(Promotion promotion, Inventory inventory) {
        adaptPromotion(promotion);

        boolean isNormalStockZero = (normalStock == 0);
        boolean canGetAnotherOne = (promotion.getFreeItemCount(promotionStock) != promotion.getFreeItemCount(
                promotionStock + 1));
        boolean hasEnoughStock = inventory.hasEnoughPromotionStock( productName, promotionStock + 1);

        return isNormalStockZero && canGetAnotherOne && hasEnoughStock;
    }

    @Override
    public int getRegularPriceCount(){
        return normalStock + promotionStock - promotionAppliedStock;
    }

    @Override
    public void updateNormalStock(int stockChange){
        normalStock += stockChange;
    }

    @Override
    public void updatePromotionStock(int stockChange){
        promotionStock += stockChange;
        if(stockChange == 1){
            promotionAppliedStock = promotionStock;
            freeStock += stockChange;
        }

    }

    @Override
    public void removeUnAppliedStock(){
        normalStock = 0;
        promotionStock = promotionAppliedStock;
    }

    private void adaptPromotion(Promotion promotion) {
        promotionAppliedStock = promotion.getApplicableItemCount(promotionStock);
        freeStock =  promotion.getFreeItemCount(promotionStock);
    }


}
