package store.domain.purchase.impl;

import store.domain.purchase.Order;

public class StoreOrder implements Order {
    private final String productName;
    private int normalQuantity;
    private int promotionQuantity;
    private int totalPrice;
    private int discountAmount = 0;

    private StoreOrder(String productName, int normalQuantity, int promotionQuantity, int price) {
        this.productName = productName;
        this.normalQuantity = normalQuantity;
        this.promotionQuantity = promotionQuantity;
        this.totalPrice = (normalQuantity + promotionQuantity) * price;
    }


    public static StoreOrder of(String productName, int normalQuantity, int promotionQuantity, int price) {
        return new StoreOrder(productName, normalQuantity, promotionQuantity, price);
    }
}
