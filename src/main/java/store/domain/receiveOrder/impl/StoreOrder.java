package store.domain.receiveOrder.impl;

import store.domain.receiveOrder.Order;
import store.domain.storeOpen.Inventory;
import store.domain.storeOpen.Promotion;

public class StoreOrder implements Order {
    private final String productName;
    private int normalQuantity;
    private int promotionQuantity;
    private int totalPrice;
    private int freeQuantity = 0;
    private final String promotionName;

    private StoreOrder(String productName, int normalQuantity, int promotionQuantity, int price, String promotionName) {
        this.productName = productName;
        this.normalQuantity = normalQuantity;
        this.promotionQuantity = promotionQuantity;
        this.totalPrice = (normalQuantity + promotionQuantity) * price;
        this.promotionName = promotionName;
    }


    public static StoreOrder of(String productName, int normalQuantity, int promotionQuantity, int price, String promotionName) {
        return new StoreOrder(productName, normalQuantity, promotionQuantity, price, promotionName);
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

        boolean isNormalQuantityZero = (normalQuantity == 0);
        boolean canGetAnotherOne = (promotion.getFreeItemCount(promotionQuantity) != promotion.getFreeItemCount(promotionQuantity + 1));
        boolean hasEnoughStock = inventory.hasEnoughPromotionStock( productName,promotionQuantity + 1);

        return isNormalQuantityZero && canGetAnotherOne && hasEnoughStock;
    }

    @Override
    public int getRegularPriceCount(){
        return normalQuantity + promotionQuantity - freeQuantity;
    }

    private void adaptPromotion(Promotion promotion) {
        freeQuantity =  promotion.getFreeItemCount( promotionQuantity );
    }


}
