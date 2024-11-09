package store.domain.impl;

import java.time.LocalDate;
import java.util.List;
import store.domain.Builder;
import store.domain.Promotion;
import store.domain.Promotions;
import store.utils.CountParser;
import store.utils.LocalDateParser;
import store.utils.Parser;

public class PromotionsBuilder implements Builder<Promotions,List<String>> {
    private final Parser<Integer> countParser = new CountParser();
    private final Parser<LocalDate> localDateParser = new LocalDateParser();

    @Override
    public Promotions build(List<String> rawPromotions) {
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
