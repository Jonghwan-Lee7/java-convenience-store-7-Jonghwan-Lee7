package store.domain.model.impl;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;
import store.domain.model.Promotion;

public class StorePromotion implements Promotion {
    private final int buyCount;
    private final int getCount;

    private final LocalDate startDate;
    private final LocalDate endDate;


    private StorePromotion(int buyCount, int getCount, LocalDate startDate, LocalDate endDate) {
        this.buyCount = buyCount;
        this.getCount = getCount;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static StorePromotion of(int buyCount, int getCount, LocalDate startDate, LocalDate endDate){
        return new StorePromotion(buyCount, getCount, startDate, endDate);
    }


    @Override
    public boolean isActive() {
        LocalDate currentDate = DateTimes.now().toLocalDate();
        return (currentDate.isEqual(startDate) || currentDate.isAfter(startDate)) &&
                (currentDate.isEqual(endDate) || currentDate.isBefore(endDate));
    }

    @Override
    public int getApplicableItemCount (int purchaseCount) {
        return (purchaseCount / (buyCount + getCount))  * (buyCount + getCount);
    }


    @Override
    public int getFreeItemCount (int purchaseCount) {
        return (purchaseCount / (buyCount + getCount))  * getCount ;
    }

}
