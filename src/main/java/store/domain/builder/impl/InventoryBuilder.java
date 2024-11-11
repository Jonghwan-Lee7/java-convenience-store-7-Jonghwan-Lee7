package store.domain.builder.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import store.domain.builder.InputBuilder;
import store.domain.model.Inventory;
import store.domain.model.Product;
import store.domain.model.impl.StoreInventory;
import store.domain.model.impl.StoreProduct;
import store.utils.parser.SingleParser;

public class InventoryBuilder implements InputBuilder<Inventory> {

    private final static int NO_STOCK = 0;
    private final static int FOR_PRICE= 1;
    private final static int FOR_STOCK = 2;
    private final static int FOR_PRODUCT_NAME = 3;
    private final static int SIZE_PER_PRODUCT = 4;
    private final static int DATA_START_POINT = 4;
    private final static String NO_PROMOTION = "null";


    private final SingleParser<Integer> positiveIntParser;

    public InventoryBuilder( SingleParser<Integer> positiveIntParser ) {
        this.positiveIntParser = positiveIntParser;
    }

    @Override
    public Inventory build(List<String> rawProducts) {
        Map<String, Product> products = new HashMap<>();

        for (int index = SIZE_PER_PRODUCT; index < rawProducts.size(); index += DATA_START_POINT) {
            String name = rawProducts.get(index);
            processProduct(products, rawProducts, index, name);
        }

        return StoreInventory.from(products);
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
        int stock = positiveIntParser.parse(rawProducts.get(index + FOR_STOCK));
        String promotionName = rawProducts.get(index + FOR_PRODUCT_NAME);

        products.get(name).addStock(stock);
        products.get(name).updatePromotion(promotionName);
    }



    private Product buildProduct (List<String> rawProducts, int index) {
        int price = positiveIntParser.parse(rawProducts.get(index + FOR_PRICE));
        int stock = positiveIntParser.parse(rawProducts.get(index + FOR_STOCK));
        String promotionName = rawProducts.get(index + FOR_PRODUCT_NAME);

        if (promotionName.equals(NO_PROMOTION)) {
            return StoreProduct.of(price,stock,NO_STOCK,null);
        }

        return StoreProduct.of(price,NO_STOCK,stock,promotionName);
    }
}
