package store.domain.model.impl;

import static store.constants.Answer.NO;
import static store.constants.Answer.YES;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import store.domain.model.Order;
import store.domain.model.Orders;
import store.domain.model.Inventory;
import store.domain.model.Promotion;
import store.domain.model.Promotions;
import store.dto.FinalOrderDTO;
import store.dto.FinalPromotionDTO;
import store.dto.InsufficientStockDTO;

public class StoreOrders implements Orders {
    private static final int ADDITIONAL_FREE_ONE = 1;
    private static final int NO_REGULAR_PRICE_COUNT = 0;
    private static final int NO_FREE_PRICE_COUNT = 0;

    private final List<Order> orders;

    private StoreOrders(List<Order> orders) {
        this.orders = orders;
    }

    public static StoreOrders of(List<Order> orders) {
        return new StoreOrders(orders);
    }

    @Override
    public List<String> getOrdersWithAdditionalOffer(Promotions promotions, Inventory inventory){
        List<String> ordersWithAdditionalOffer = new ArrayList<>();

        for (Order order: orders){
            if (isAdditionalOffer(order, promotions, inventory)){
                ordersWithAdditionalOffer.add(order.getProductName());
            }
        }
        return ordersWithAdditionalOffer;
    }

    @Override
    public List<InsufficientStockDTO> getInsufficientPromotionStocks(Promotions promotions,
                                                                     Set<String> decisionExceptions){
        List<InsufficientStockDTO> insufficientStocks = new ArrayList<>();

        for (Order order: orders){
            if ( isPromotionStockInsufficient(order, promotions, decisionExceptions)){
                int insufficientCount = order.getRegularPriceCount();
                String productName = order.getProductName();
                insufficientStocks.add(new InsufficientStockDTO(productName, insufficientCount));
            }
        }
        return insufficientStocks;
    }

    @Override
    public void applyAdditionDecision(Map<String, String> customerDecisions){
        Map<String,Integer> promotionStockChanges = new HashMap<>();

        for (Map.Entry<String, String> decision : customerDecisions.entrySet()) {
            if (YES.getMessage().equals(decision.getValue())) {
                promotionStockChanges.put(decision.getKey(), ADDITIONAL_FREE_ONE);
            }
        }
        applyToPromotionStocks(promotionStockChanges);
    }


    @Override
    public void applyInsufficientPromotionStock(Map<String, String> customerDecisions){
        Set<String> targetsToQuitStock = new HashSet<>();
        for (Map.Entry<String, String> decision : customerDecisions.entrySet()) {
            if (NO.getMessage().equals(decision.getValue())) {
                targetsToQuitStock.add(decision.getKey());
            }
        }

        removeUnAppliedStock(targetsToQuitStock);
    }

    @Override
    public List<FinalOrderDTO> getFinalOrderDTOs(){
        List<FinalOrderDTO> finalOrders = new ArrayList<>();
        for (Order order: orders){
            finalOrders.add(order.getFinalOrderDTO());
        }
        return finalOrders;
    }

    @Override
    public List<FinalPromotionDTO> getFinalPromotionDTOs(){
        List<FinalPromotionDTO> finalPromotions = new ArrayList<>();

        for (Order order: orders){
            FinalPromotionDTO finalPromotionDTO = order.getFinalPromotionDTO();
            if (finalPromotionDTO.freeCount() == NO_FREE_PRICE_COUNT) {
                continue;
            }
            finalPromotions.add(finalPromotionDTO);
        }
        return finalPromotions;
    }
    
    private void removeUnAppliedStock(Set<String> targetsToQuitStock){
        for (Order order: orders){
            String productName =  order.getProductName();
            if (targetsToQuitStock.contains(productName)){
                order.removeUnAppliedStock();
            }
        }
    }

    private void applyToPromotionStocks(Map<String,Integer> promotionStockChanges) {
        for (Order order: orders){
            String productName = order.getProductName();

            if(promotionStockChanges.containsKey(productName)){
                int stockChanges = promotionStockChanges.get(productName);
                updatePromotionStocks(order, stockChanges);
            }
        }
    }


    private void updatePromotionStocks(Order order, int stockChange){
        order.updatePromotionStock(stockChange);
    }


    private boolean isPromotionStockInsufficient(Order order, Promotions promotions,Set<String> decisionExceptions){
        String productName = order.getProductName();
        if (decisionExceptions.contains(productName)) {
            return false;
        }
        if ( isPromotionValid(order,promotions)){
            return order.getRegularPriceCount() != NO_REGULAR_PRICE_COUNT;
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
