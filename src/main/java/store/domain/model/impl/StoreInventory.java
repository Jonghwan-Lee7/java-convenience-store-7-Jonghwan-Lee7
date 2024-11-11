package store.domain.model.impl;

import static store.exception.ErrorMessages.INVALID_PRODUCT_NAME;
import static store.exception.ErrorMessages.INVALID_PROMOTION_NAME;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import store.domain.model.Product;
import store.domain.model.Inventory;
import store.dto.FinalOrderDTO;
import store.dto.FormattedStockDTO;
import store.utils.DTOMapper;

public class StoreInventory implements Inventory {
    private final Map<String, Product> products;

    private StoreInventory( Map<String, Product> products) {
        this.products = products;
    }

    public static StoreInventory from(Map<String, Product> products) {
        return new StoreInventory(products);
    }

    @Override
    public List<FormattedStockDTO> toDTOs(){
        List<FormattedStockDTO> formattedStockDTOS = new ArrayList<>();

        for (String productName : products.keySet()) {
            Product product = products.get(productName);
            FormattedStockDTO formattedStockDTO =  DTOMapper.toStockDTO(product,productName);
            formattedStockDTOS.add(formattedStockDTO);
        }
        return formattedStockDTOS;
    }


    @Override
    public List<Integer> getPurchaseDetails(String productName, int quantity){
        validatePurchase(productName);
        Product product = products.get(productName);
        return product.getPurchaseDetails(quantity);
    }

    @Override
    public String getPromotionName(String productName){
        Product product = products.get(productName);
        return product.getPromotionName();
    }

    @Override
    public boolean hasEnoughPromotionStock(String productName, int promotionQuantity){
        validatePurchase(productName);
        Product product = products.get(productName);
        return product.hasEnoughPromotionStock(promotionQuantity);
    }

    @Override
    public void updateStocks(List<FinalOrderDTO> finalOrderDTOS){
        for (FinalOrderDTO finalOrderDTO : finalOrderDTOS) {
            String productName = finalOrderDTO.productName();
            int soldNormalStock = finalOrderDTO.normalStockCount();
            int soldPromotionStock = finalOrderDTO.promotionStockCount();
            Product product = products.get(productName);
            product.updateStocks(soldNormalStock, soldPromotionStock);
        }
    }

    @Override
    public void validateProductPromotion(Set<String> promotions){
        for ( Product product:  products.values()){
            String promotionName  = product.getPromotionName();
            if (promotionName != null && !promotions.contains(promotionName)){
                throw new IllegalArgumentException(INVALID_PROMOTION_NAME.getErrorMessage());
            }

        }
    }


    private void validatePurchase(String productName) {

        if ( !products.containsKey(productName)) {
            throw new IllegalArgumentException(INVALID_PRODUCT_NAME.getErrorMessage());
        }

    }
}
