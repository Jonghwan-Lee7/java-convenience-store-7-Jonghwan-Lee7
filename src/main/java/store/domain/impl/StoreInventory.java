package store.domain.impl;

import java.util.Map;
import store.domain.Inventory;
import store.domain.Product;

public class StoreInventory implements Inventory {
    private final Map<String, Product> products;

    private StoreInventory( Map<String, Product> products) {
        this.products = products;
    }

    public static StoreInventory create(Map<String, Product> products) {
        return new StoreInventory(products);
    }
}
