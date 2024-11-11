package store.domain.repository.impl;

import java.util.Optional;
import store.domain.model.Inventory;
import store.domain.repository.SingleRepository;

public class InventoryRepository implements SingleRepository<Inventory> {
    private Inventory inventory;

    @Override
    public void save(Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public Optional<Inventory> get() {
        return Optional.ofNullable(this.inventory);
    }


}
