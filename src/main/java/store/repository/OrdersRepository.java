package store.repository;

import java.util.Optional;
import store.domain.purchase.Orders;

public class OrdersRepository implements SingleRepository<Orders> {
    private  Orders orders;

    @Override
    public Optional<Orders> get() {
        return Optional.empty();
    }

    @Override
    public Orders save(Orders orders) {
        this.orders = orders;
        return orders;
    }
}
