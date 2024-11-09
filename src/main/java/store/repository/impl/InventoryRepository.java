package store.repository.impl;

import java.util.Optional;
import store.domain.Inventory;
import store.repository.SingleRepository;

public class InventoryRepository implements SingleRepository<Inventory> {
    private Inventory inventory;

    @Override
    public Inventory save(Inventory inventory) {
        this.inventory = inventory;
        return this.inventory;
    }

    @Override
    public Optional<Inventory> get() {
        return Optional.ofNullable(this.inventory);
    }


}
