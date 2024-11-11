package store.domain.builder.impl;

import static store.exception.ErrorMessages.INVALID_FORMAT;

import java.util.ArrayList;
import java.util.List;
import store.domain.builder.TwoInputsBuilder;
import store.domain.model.Order;
import store.domain.model.Orders;
import store.domain.model.impl.StoreOrder;
import store.domain.model.impl.StoreOrders;
import store.domain.model.Inventory;

public class OrdersBuilder implements TwoInputsBuilder<Orders,Inventory> {
    private final String DELIMITER = ",";
    private final String REGEX_FOR_ORDER = "^\\[(.+?)-([0-9]+)]$";

    @Override
    public Orders build(String rawOrderTest, Inventory inventory) {
        List<Order> orders = new ArrayList<>();
        String[] rawOrders = rawOrderTest.split(DELIMITER);

        for (String rawOrder : rawOrders) {
            Order order =  buildOrder(rawOrder, inventory);
            orders.add(order);
        }

        return StoreOrders.of(orders);
    }

    private Order buildOrder(String rawOrder, Inventory inventory) {
        validate(rawOrder);
        String[] orderDetails =  extractProductDetails(rawOrder);
        String productName = orderDetails[0];
        int quantity = Integer.parseInt(orderDetails[1]);
        return generateOrder(productName,quantity,inventory);
    }

    private Order generateOrder(String productName, int quantity, Inventory inventory) {
        List<Integer> details =  inventory.getPurchaseDetails(productName,quantity);
        int promotionQuantity = details.getFirst();
        int normalQuantity = quantity - promotionQuantity;
        int price = details.getLast();
        String promotionName = inventory.getPromotionName(productName);

        return StoreOrder.of( productName,normalQuantity,promotionQuantity, price, promotionName);

    }


    private void validate(String rawOrder) {
        if (!rawOrder.matches(REGEX_FOR_ORDER)){
            throw new IllegalArgumentException(INVALID_FORMAT.getErrorMessage());
        }
    }

    private String[] extractProductDetails(String rawOrder) {
        String[] orderDetails = new String[2];
        orderDetails[0] = rawOrder.replaceAll(REGEX_FOR_ORDER, "$1");
        orderDetails[1] = rawOrder.replaceAll(REGEX_FOR_ORDER, "$2");
        return orderDetails;
    }
}
