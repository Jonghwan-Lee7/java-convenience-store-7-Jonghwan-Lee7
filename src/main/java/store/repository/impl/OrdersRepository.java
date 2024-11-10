package store.repository.impl;

import java.util.Optional;
import store.domain.receiveOrder.Orders;
import store.repository.SingleRepository;

public class OrdersRepository implements SingleRepository<Orders> {
    private  Orders orders;

    @Override
    public Orders save(Orders orders) {
        this.orders = orders;
        return orders;
    }

    @Override
    public Optional<Orders> get() {
        return Optional.ofNullable(orders);
    }


}
