package store.service.impl;

import java.util.List;
import store.domain.FileReader;
import store.domain.Promotions;
import store.domain.impl.PromotionsBuilder;
import store.domain.impl.StoreFileReader;
import store.repository.impl.PromotionsRepository;
import store.service.StoreOpenService;

public class StoreOpenServiceImpl implements StoreOpenService {
    private final PromotionsRepository promotionsRepository;
    private final PromotionsBuilder promotionsBuilder;

    private final FileReader fileReader = new StoreFileReader();

    public StoreOpenServiceImpl(PromotionsRepository promotionsRepository,
                                PromotionsBuilder promotionsBuilder) {

        this.promotionsRepository = promotionsRepository;
        this.promotionsBuilder = promotionsBuilder;
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


    }

}
