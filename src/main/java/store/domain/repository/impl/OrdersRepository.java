package store.domain.repository.impl;

import java.util.Optional;
import store.domain.model.Orders;
import store.domain.repository.SingleRepository;

public class OrdersRepository implements SingleRepository<Orders> {
    private  Orders orders;

    @Override
    public void save(Orders orders) {
        this.orders = orders;
    }

    @Override
    public Optional<Orders> get() {
        return Optional.ofNullable(orders);
    }


}
