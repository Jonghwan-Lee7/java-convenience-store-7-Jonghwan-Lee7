package store.domain.model.impl;

import store.domain.model.Order;
import store.domain.model.Inventory;
import store.domain.model.Promotion;
import store.dto.FinalOrderDTO;
import store.dto.FinalPromotionDTO;

public class StoreOrder implements Order {

    private static final int NO_NORMAL_STOCK = 0;
    private static final int ADDITIONAL_FREE_STOCK = 1;

    private final String productName;
    private int normalStock;
    private int promotionStock;
    private int promotionAppliedStock = 0;
    private final int price;
    private int freeStock = 0;
    private final String promotionName;

    private StoreOrder(String productName, int normalStock, int promotionStock, int price, String promotionName) {
        this.productName = productName;
        this.normalStock = normalStock;
        this.promotionStock = promotionStock;
        this.price = price;
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

        boolean isNormalStockZero = (normalStock == NO_NORMAL_STOCK);
        boolean canGetAnotherOne = (promotion.getFreeItemCount(promotionStock) != promotion.getFreeItemCount(
                promotionStock + ADDITIONAL_FREE_STOCK));
        boolean hasEnoughStock = inventory.hasEnoughPromotionStock( productName, promotionStock + ADDITIONAL_FREE_STOCK);

        return isNormalStockZero && canGetAnotherOne && hasEnoughStock;
    }

    @Override
    public int getRegularPriceCount(){
        return normalStock + promotionStock - promotionAppliedStock;
    }

    @Override
    public void updatePromotionStock(int stockChange){
        promotionStock += stockChange;
        if(stockChange == ADDITIONAL_FREE_STOCK){
            promotionAppliedStock = promotionStock;
            freeStock += stockChange;
        }
    }

    @Override
    public void removeUnAppliedStock(){
        normalStock = NO_NORMAL_STOCK;
        promotionStock = promotionAppliedStock;
    }

    @Override
    public FinalOrderDTO getFinalOrderDTO(){
        int finalPurchaseCount = normalStock + promotionStock;
        int totalPrice = price * finalPurchaseCount;
        return new FinalOrderDTO(productName,normalStock,promotionStock,totalPrice);
    }

    @Override
    public FinalPromotionDTO getFinalPromotionDTO(){
        int freeCount = freeStock;
        int promotionAppliedCount = promotionAppliedStock;
        int discountAmount = freeCount * price;

        return new FinalPromotionDTO(productName,freeCount,promotionAppliedCount,discountAmount);
    }

    private void adaptPromotion(Promotion promotion) {
        promotionAppliedStock = promotion.getApplicableItemCount(promotionStock);
        freeStock =  promotion.getFreeItemCount(promotionStock);
    }


}
