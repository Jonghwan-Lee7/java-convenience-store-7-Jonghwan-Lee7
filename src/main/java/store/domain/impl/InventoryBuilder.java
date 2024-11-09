package store.domain.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import store.domain.Inventory;
import store.domain.Product;
import store.domain.SingleBuilder;
import store.utils.SingleParser;

public class InventoryBuilder implements SingleBuilder<Inventory, List<String>> {
    private final SingleParser<Integer> positiveIntParser;

    public InventoryBuilder( SingleParser<Integer> positiveIntParser ) {
        this.positiveIntParser = positiveIntParser;
    }

    @Override
    public Inventory build(List<String> rawProducts) {
        Map<String, Product> products = new HashMap<>();

        for (int index = 4; index < rawProducts.size(); index += 4) {
            String name = rawProducts.get(index);
            processProduct(products, rawProducts, index, name);
        }

        return StoreInventory.create(products);
    }

    private void processProduct(Map<String, Product> products, List<String> rawProducts, int index, String name) {
        if (products.containsKey(name)) {
            updateProduct(products, rawProducts, index);
        }

        if (!products.containsKey(name)) {
            products.put(name, buildProduct(rawProducts, index));
        }
    }

    private void updateProduct(Map<String, Product> products, List<String> rawProducts, int index) {
        String name = rawProducts.get(index);
        int stock = positiveIntParser.parse(rawProducts.get(index + 2)); // validate 메서드로 바꿔야함
        String promotionName = rawProducts.get(index + 3);

        products.get(name).addStock(stock);
        products.get(name).updatePromotion(promotionName);
    }



    private Product buildProduct (List<String> rawProducts, int index) {
        int price = positiveIntParser.parse(rawProducts.get(index + 1));
        int stock = positiveIntParser.parse(rawProducts.get(index + 2));
        String promotionName = rawProducts.get(index + 3);
        if (promotionName.equals("null")) {
            return StoreProduct.create(price,stock,0,null);

        }

        return StoreProduct.create(price,0,stock,promotionName);


    }
}
