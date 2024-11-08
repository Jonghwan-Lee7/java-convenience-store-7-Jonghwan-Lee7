package store.domain.impl;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;
import store.domain.Promotion;

public class StorePromotion implements Promotion {
    private final int buyCount;
    private final int getCount;

    private final LocalDate startDate;
    private final LocalDate endDate;


    public StorePromotion(int buyCount, int getCount, LocalDate startDate, LocalDate endDate) {
        this.buyCount = buyCount;
        this.getCount = getCount;
        this.startDate = startDate;
        this.endDate = endDate;
    }


    public boolean isActive( ) {
        LocalDate currentDate = DateTimes.now().toLocalDate();
        return (currentDate.isEqual(startDate) || currentDate.isAfter(startDate)) &&
                (currentDate.isEqual(endDate) || currentDate.isBefore(endDate));
    }


    // 만약 한 품목의 구매 총량을 넣었을 때의 반환 값과
    // 프로모션 재고에서 차감되는 양을 넣을 때의 반환결과가 다르면 이는 프로모션 재고 부족 문제.
    public int getApplicableItemCount (int purchaseCount) {
        return (purchaseCount / (buyCount + getCount))  * (buyCount * getCount);
    }


    public int getFreeItemCount (int purchaseCount) {
        return (purchaseCount / (buyCount + getCount))  * getCount ;
    }

    public boolean canGetFreeItem( int purchaseCount) {
        return  getFreeItemCount( purchaseCount ) !=  getFreeItemCount( purchaseCount + 1 );
    }
}
