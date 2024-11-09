package store.service.impl;

import java.time.LocalDate;
import java.util.List;
import store.domain.FileReader;
import store.domain.Promotion;
import store.domain.Promotions;
import store.domain.impl.StoreFileReader;
import store.domain.impl.StorePromotion;
import store.domain.impl.StorePromotions;
import store.repository.impl.PromotionsRepository;
import store.service.StoreOpenService;
import store.utils.CountParser;
import store.utils.LocalDateParser;
import store.utils.Parser;

public class StoreOpenServiceImpl implements StoreOpenService {
    private final PromotionsRepository promotionsRepository;

    private final FileReader fileReader = new StoreFileReader();
    private final Parser<LocalDate> localDateParser = new LocalDateParser();
    private final Parser<Integer> countParser = new CountParser();

    public StoreOpenServiceImpl(PromotionsRepository promotionsRepository) {
        this.promotionsRepository = promotionsRepository;
    }



    @Override
    public void loadPromotions() {
        List<String> rawPromotions = fileReader.getRawPromotions();

        Promotions promotions = buildPromotions(rawPromotions);

        // rawPromotions을 이용해서 promotions에 Promotion 객체를 넣는 메서드를 여기에 추가
        promotionsRepository.save(promotions);
    }

    @Override
    public void loadInventory() {
        List<String> rawInventory = fileReader.getRawProducts();




    }

    private Promotions buildPromotions(List<String> rawPromotions) {
        Promotions promotions = StorePromotions.create();

        for (int index = 0; index < rawPromotions.size(); index += 5) {
            Promotion promotion = buildPromotion(rawPromotions, index);
            String name = rawPromotions.get(index);
            promotions.add(name, promotion);
        }
        return promotions;
    }

    private Promotion buildPromotion(List<String> rawPromotions, int index) {
        int buyCount = countParser.parse(rawPromotions.get(index + 1));
        int getCount = countParser.parse(rawPromotions.get(index + 2));
        LocalDate startDate = localDateParser.parse(rawPromotions.get(index + 3));
        LocalDate endDate = localDateParser.parse(rawPromotions.get(index + 4));

        return StorePromotion.create(buyCount, getCount, startDate, endDate);
    }
}
