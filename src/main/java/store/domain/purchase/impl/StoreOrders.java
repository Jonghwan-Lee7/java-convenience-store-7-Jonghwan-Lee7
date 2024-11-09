package store.domain.purchase.impl;

import java.util.List;
import store.domain.purchase.Order;
import store.domain.purchase.Orders;

public class StoreOrders implements Orders {
    List<Order> orders;

    private StoreOrders(List<Order> orders) {
        this.orders = orders;
    }

    public static StoreOrders of(List<Order> orders) {
        return new StoreOrders(orders);
    }
}
