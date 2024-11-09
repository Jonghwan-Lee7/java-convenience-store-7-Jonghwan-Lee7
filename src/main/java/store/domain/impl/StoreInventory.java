package store.domain.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import store.domain.Inventory;
import store.domain.Product;
import store.dto.StockDTO;

public class StoreInventory implements Inventory {
    private final Map<String, Product> products;

    private StoreInventory( Map<String, Product> products) {
        this.products = products;
    }

    public static StoreInventory create(Map<String, Product> products) {
        return new StoreInventory(products);
    }

    @Override
    public List<StockDTO> toDTOs(){
        List<StockDTO> stockDTOs = new ArrayList<>();

        for (String productName : products.keySet()) {
            Product product = products.get(productName);
            stockDTOs.add(new StockDTO(product.toFormattedString(productName)));
        }
        return stockDTOs;
    }
}
