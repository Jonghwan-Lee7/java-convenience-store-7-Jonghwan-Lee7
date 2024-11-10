package store.domain.receiveOrder.impl;

import store.domain.receiveOrder.Order;

public class StoreOrder implements Order {
    private final String productName;
    private int normalQuantity;
    private int promotionQuantity;
    private int totalPrice;
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
}
