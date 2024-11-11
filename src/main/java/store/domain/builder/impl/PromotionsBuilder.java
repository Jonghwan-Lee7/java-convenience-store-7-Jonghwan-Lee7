package store.domain.builder.impl;

import java.time.LocalDate;
import java.util.List;
import store.domain.builder.InputBuilder;
import store.domain.model.Promotion;
import store.domain.model.Promotions;
import store.domain.model.impl.StorePromotion;
import store.domain.model.impl.StorePromotions;
import store.utils.parser.SingleParser;

public class PromotionsBuilder implements InputBuilder<Promotions> {
    private final static int FOR_BUT_COUNT = 1;
    private final static int FOR_GET_COUNT = 2;
    private final static int FOR_START_DATE = 3;
    private final static int FOR_END_DATE = 4;
    private final static int SIZE_PER_PROMOTION = 5;
    private final static int DATA_START_POINT = 5;

    private final SingleParser<Integer> positiveIntParser;
    private final SingleParser<LocalDate> localDateParser;


    public PromotionsBuilder( SingleParser<Integer> positiveIntParser, SingleParser<LocalDate> localDateParser ) {
        this.positiveIntParser = positiveIntParser;
        this.localDateParser = localDateParser;
    }

    @Override
    public Promotions build(List<String> rawPromotions) {
        Promotions promotions = StorePromotions.create();

        for (int index = DATA_START_POINT; index < rawPromotions.size(); index += SIZE_PER_PROMOTION) {
            Promotion promotion = buildPromotion(rawPromotions, index);
            String name = rawPromotions.get(index);
            promotions.add(name, promotion);
        }
        return promotions;
    }

    private Promotion buildPromotion(List<String> rawPromotions, int index) {
        int buyCount = positiveIntParser.parse(rawPromotions.get(index + FOR_BUT_COUNT));
        int getCount = positiveIntParser.parse(rawPromotions.get(index + FOR_GET_COUNT));
        LocalDate startDate = localDateParser.parse(rawPromotions.get(index + FOR_START_DATE));
        LocalDate endDate = localDateParser.parse(rawPromotions.get(index + FOR_END_DATE));

        return StorePromotion.create(buyCount, getCount, startDate, endDate);
    }
}
