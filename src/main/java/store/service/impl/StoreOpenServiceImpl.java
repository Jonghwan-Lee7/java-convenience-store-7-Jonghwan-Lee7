package store.service.impl;

import java.util.List;
import store.domain.FileReader;
import store.domain.Inventory;
import store.domain.Promotions;
import store.domain.SingleBuilder;
import store.domain.impl.StoreFileReader;
import store.repository.SingleRepository;
import store.service.StoreOpenService;

public class StoreOpenServiceImpl implements StoreOpenService {
    private final SingleRepository<Promotions> promotionsRepository;
    private final SingleRepository<Inventory> inventoryRepository;
    private final SingleBuilder<Promotions,List<String>> promotionsBuilder;
    private final SingleBuilder<Inventory,List<String>> inventoryBuilder;

    private final FileReader fileReader = new StoreFileReader();

    public StoreOpenServiceImpl(SingleRepository<Promotions> promotionsRepository,
                                SingleRepository<Inventory> inventoryRepository,
                                SingleBuilder<Promotions,List<String>>  promotionsBuilder,
                                SingleBuilder<Inventory,List<String>> inventoryBuilder) {

        this.promotionsRepository = promotionsRepository;
        this.inventoryRepository = inventoryRepository;
        this.promotionsBuilder = promotionsBuilder;
        this.inventoryBuilder = inventoryBuilder;

    }


    @Override
    public void loadPromotions() {

        List<String> rawPromotions = fileReader.getRawPromotions();
        Promotions promotions = promotionsBuilder.build(rawPromotions);
        promotionsRepository.save(promotions);
    }

    @Override
    public void loadInventory() {

        List<String> rawInventory = fileReader.getRawProducts();
        Inventory inventory = inventoryBuilder.build(rawInventory);
        inventoryRepository.save(inventory);
    }

}
