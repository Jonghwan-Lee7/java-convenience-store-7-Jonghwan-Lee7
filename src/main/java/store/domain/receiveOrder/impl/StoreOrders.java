package store.domain.receiveOrder.impl;

import java.util.List;
import store.domain.receiveOrder.Order;
import store.domain.receiveOrder.Orders;

public class StoreOrders implements Orders {
    List<Order> orders;

    private StoreOrders(List<Order> orders) {
        this.orders = orders;
    }

    public static StoreOrders of(List<Order> orders) {
        return new StoreOrders(orders);
    }
}
