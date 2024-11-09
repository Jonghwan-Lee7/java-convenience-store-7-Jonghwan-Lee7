package store.service.impl;

import static store.exception.ErrorMessages.NO_SAVED_INVENTORY;

import store.domain.builder.TwoInputsBuilder;
import store.domain.purchase.Orders;
import store.domain.storeOpen.Inventory;
import store.exception.EntityNotFoundException;
import store.repository.SingleRepository;
import store.service.TakeOrderService;

public class TakeOrderServiceImpl implements TakeOrderService {
    private final TwoInputsBuilder<Orders,Inventory> ordersBuilder;
    private final SingleRepository<Inventory> inventoryRepository;
    private final SingleRepository<Orders> ordersRepository;

    public TakeOrderServiceImpl(TwoInputsBuilder<Orders,Inventory> ordersBuilder,
                                SingleRepository<Inventory> inventoryRepository,
                                SingleRepository<Orders> ordersRepository) {

        this.ordersBuilder = ordersBuilder;
        this.inventoryRepository = inventoryRepository;
        this.ordersRepository = ordersRepository;

    }

    @Override
    public void takeOrder(String rawOrder) {
        Inventory inventory = inventoryRepository.get()
                        .orElseThrow(()-> new EntityNotFoundException(NO_SAVED_INVENTORY.getErrorMessage()));

        Orders orders = ordersBuilder.build(rawOrder, inventory);
        ordersRepository.save(orders);
    }
}
