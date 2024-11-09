package store.service.impl;

import static store.exception.ErrorMessages.NO_SAVE_INVENTORY;

import java.util.List;
import store.domain.storeOpen.FileReader;
import store.domain.storeOpen.Inventory;
import store.domain.storeOpen.Promotions;
import store.domain.builder.SingleBuilder;
import store.domain.storeOpen.impl.StoreFileReader;
import store.dto.StockDTO;
import store.exception.EntityNotFoundException;
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

    @Override
    public List<StockDTO> createStockDTOs(){
        Inventory inventory =  inventoryRepository.get()
                .orElseThrow(()-> new EntityNotFoundException(NO_SAVE_INVENTORY.getMessage()));

        return inventory.toDTOs();
    }

}
