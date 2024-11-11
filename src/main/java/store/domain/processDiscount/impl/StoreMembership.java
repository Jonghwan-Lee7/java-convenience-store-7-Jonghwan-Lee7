package store.domain.processDiscount.impl;

import static java.lang.Math.min;

import store.constants.Answer;
import store.domain.processDiscount.Membership;

public class StoreMembership implements Membership {
    private final static int DISCOUNT_LIMIT = 8000;
    private final static double DISCOUNT_RATE = 0.3;
    private final static int NO_DISCOUNT = 0;

    private final boolean canApplyDiscount;

    private StoreMembership( boolean canApplyDiscount ) {
        this.canApplyDiscount = canApplyDiscount;
    }

    public static StoreMembership create(String answer){
        if (answer.equals(Answer.YES.getMessage())){
            return new StoreMembership(true);
        }
        return new StoreMembership(false);
    }

    @Override
    public int getDiscountAmount(int totalPurchaseAmount, int promotionAppliedAmount){
        int discountAppliedAmount = totalPurchaseAmount - promotionAppliedAmount;
        if(canApplyDiscount){
            return min( DISCOUNT_LIMIT,  (int) Math.floor(discountAppliedAmount * DISCOUNT_RATE) );
        }
        return NO_DISCOUNT;
    }


}
