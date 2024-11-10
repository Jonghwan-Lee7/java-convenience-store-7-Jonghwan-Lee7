package store.domain.storeOpen.impl;

import static store.exception.ErrorMessages.INVALID_PRODUCT_NAME;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import store.domain.storeOpen.Inventory;
import store.domain.storeOpen.Product;
import store.dto.StockDTO;
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
    public List<StockDTO> toDTOs(){
        List<StockDTO> stockDTOs = new ArrayList<>();

        for (String productName : products.keySet()) {
            Product product = products.get(productName);
            StockDTO stockDTO =  DTOMapper.toStockDTO(product,productName);
            stockDTOs.add(stockDTO);
        }
        return stockDTOs;
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


    private void validatePurchase(String productName) {

        if ( !products.containsKey(productName)) {
            throw new IllegalArgumentException(INVALID_PRODUCT_NAME.getErrorMessage());
        }

    }
}
