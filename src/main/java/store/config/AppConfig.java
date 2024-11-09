package store.config;

import java.time.LocalDate;
import java.util.List;
import store.domain.Inventory;
import store.domain.Promotions;
import store.domain.SingleBuilder;
import store.domain.impl.InventoryBuilder;
import store.domain.impl.PromotionsBuilder;
import store.repository.SingleRepository;
import store.repository.impl.InventoryRepository;
import store.repository.impl.PromotionsRepository;
import store.service.StoreOpenService;
import store.service.impl.StoreOpenServiceImpl;
import store.utils.LocalDateParser;
import store.utils.SingleParser;
import store.utils.PositiveIntParser;

public class AppConfig {

    private final static SingleRepository<Inventory> inventoryRepository = new InventoryRepository();
    private final static SingleRepository<Promotions> promotionsRepository = new PromotionsRepository();

    private final static SingleParser<Integer> positiveIntParser = new PositiveIntParser();
    private final static SingleParser<LocalDate> localDateParser = new LocalDateParser();

    private final static SingleBuilder<Promotions, List<String>> promotionsBuilder = new PromotionsBuilder(positiveIntParser, localDateParser);
    private final static SingleBuilder<Inventory, List<String>> inventoryBuilder = new InventoryBuilder(positiveIntParser);

    private final static StoreOpenService storeOpenService = new StoreOpenServiceImpl(
            promotionsRepository,
            inventoryRepository,
            promotionsBuilder,
            inventoryBuilder);

    public static StoreOpenService getStoreOpenService() {
        return storeOpenService;
    }

}
