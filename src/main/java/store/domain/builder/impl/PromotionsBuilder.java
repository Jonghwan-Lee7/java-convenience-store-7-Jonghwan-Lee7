package store.domain.builder.impl;

import java.time.LocalDate;
import java.util.List;
import store.domain.builder.InputBuilder;
import store.domain.storeOpen.Promotion;
import store.domain.storeOpen.Promotions;
import store.domain.storeOpen.impl.StorePromotion;
import store.domain.storeOpen.impl.StorePromotions;
import store.utils.SingleParser;

public class PromotionsBuilder implements InputBuilder<Promotions> {
    private final SingleParser<Integer> positiveIntParser;
    private final SingleParser<LocalDate> localDateParser;

    public PromotionsBuilder( SingleParser<Integer> positiveIntParser, SingleParser<LocalDate> localDateParser ) {
        this.positiveIntParser = positiveIntParser;
        this.localDateParser = localDateParser;
    }

    @Override
    public Promotions build(List<String> rawPromotions) {
        Promotions promotions = StorePromotions.create();

        for (int index = 5; index < rawPromotions.size(); index += 5) {
            Promotion promotion = buildPromotion(rawPromotions, index);
            String name = rawPromotions.get(index);
            promotions.add(name, promotion);
        }
        return promotions;
    }

    private Promotion buildPromotion(List<String> rawPromotions, int index) {
        int buyCount = positiveIntParser.parse(rawPromotions.get(index + 1));
        int getCount = positiveIntParser.parse(rawPromotions.get(index + 2));
        LocalDate startDate = localDateParser.parse(rawPromotions.get(index + 3));
        LocalDate endDate = localDateParser.parse(rawPromotions.get(index + 4));

        return StorePromotion.create(buyCount, getCount, startDate, endDate);
    }
}
