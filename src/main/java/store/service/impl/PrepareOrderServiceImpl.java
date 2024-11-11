package store.service.impl;

import static store.exception.ErrorMessages.NO_SAVED_INVENTORY;

import java.util.List;
import store.domain.storeOpen.FileReader;
import store.domain.model.Inventory;
import store.domain.model.Promotions;
import store.domain.builder.InputBuilder;
import store.domain.storeOpen.impl.StoreFileReader;
import store.dto.StockDTO;
import store.exception.EntityNotFoundException;
import store.domain.repository.SingleRepository;
import store.service.PrepareOrderService;

public class PrepareOrderServiceImpl implements PrepareOrderService {
    private final SingleRepository<Promotions> promotionsRepository;
    private final SingleRepository<Inventory> inventoryRepository;
    private final InputBuilder<Promotions> promotionsBuilder;
    private final InputBuilder<Inventory> inventoryBuilder;

    private final FileReader fileReader = new StoreFileReader();

    public PrepareOrderServiceImpl(SingleRepository<Promotions> promotionsRepository,
                                   SingleRepository<Inventory> inventoryRepository,
                                   InputBuilder<Promotions> promotionsBuilder,
                                   InputBuilder<Inventory> inventoryBuilder) {

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
                .orElseThrow(()-> new EntityNotFoundException(NO_SAVED_INVENTORY.getErrorMessage()));

        return inventory.toDTOs();
    }

}
