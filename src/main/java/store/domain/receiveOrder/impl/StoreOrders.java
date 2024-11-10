package store.domain.receiveOrder.impl;

import java.util.ArrayList;
import java.util.List;
import store.domain.receiveOrder.Order;
import store.domain.receiveOrder.Orders;
import store.domain.storeOpen.Inventory;
import store.domain.storeOpen.Promotion;
import store.domain.storeOpen.Promotions;
import store.dto.PromotionStockInsufficientDTO;

public class StoreOrders implements Orders {
    List<Order> orders;

    private StoreOrders(List<Order> orders) {
        this.orders = orders;
    }

    public static StoreOrders of(List<Order> orders) {
        return new StoreOrders(orders);
    }

    @Override
    public List<Order> getOrders() {
        return orders;
    }

    @Override
    public List<String> getOrdersWithAdditionalOffer(Promotions promotions, Inventory inventory){
        List<String> ordersWithAdditionalOffer = new ArrayList<>();

        for (Order order: orders){
            if (isAdditionalOffer(order, promotions, inventory)){
                ordersWithAdditionalOffer.add(order.toString());
            }
        }
        return ordersWithAdditionalOffer;
    }

    @Override
    public List<PromotionStockInsufficientDTO> getOrdersWithLackPromotionStock(Promotions promotions){
        List<PromotionStockInsufficientDTO> ordersWithLackPromotionStock = new ArrayList<>();

        for (Order order: orders){
            if (isPromotionStockInSufficient(order, promotions)){
                int insufficientCount = order.getRegularPriceCount();
                String productName = order.getProductName();
                ordersWithLackPromotionStock.add(new PromotionStockInsufficientDTO(productName, insufficientCount));
            }
        }
        return ordersWithLackPromotionStock;
    }

    private boolean isPromotionStockInSufficient(Order order, Promotions promotions){
        if ( isPromotionValid(order,promotions)){
            return order.getRegularPriceCount() != 0;
        }
        return false;
    }

    private boolean isAdditionalOffer(Order order, Promotions promotions , Inventory inventory ){
        if (isPromotionValid(order, promotions)){
            return canGetAdditionalOffer(order, promotions, inventory);
        }
        return false;
    }

    private boolean isPromotionValid(Order order, Promotions promotions) {
        String promotionName  = order.getPromotionName();
        if (promotionName == null ){
            return false;
        }

        Promotion promotion = promotions.getPromotion(promotionName);
        if (!promotion.isActive()){
            return false;
        }
        return true;
    }

    private boolean canGetAdditionalOffer(Order order, Promotions promotions, Inventory inventory){
        String promotionName  = order.getPromotionName();
        Promotion promotion = promotions.getPromotion(promotionName);
        return order.canGetAdditionalOne(promotion, inventory);
    }

}
