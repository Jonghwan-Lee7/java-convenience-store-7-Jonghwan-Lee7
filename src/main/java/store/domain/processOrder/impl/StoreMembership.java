package store.domain.processOrder.impl;

import static java.lang.Math.min;

import store.domain.processOrder.Membership;

public class StoreMembership implements Membership {
    private final int DISCOUNT_LIMIT = 8000;
    private final double DISCOUNT_RATE = 0.3;
    private final int NO_DISCOUNT = 0;

    private final boolean canApplyDiscount;

    private StoreMembership( boolean canApplyDiscount ) {
        this.canApplyDiscount = canApplyDiscount;
    }

    public static StoreMembership create(String answer){
        if (answer.equals("Y")){
            return new StoreMembership(true);
        }
        return new StoreMembership(false);
    }

    @Override
    public int getDiscountAmount(int amount){
        if(canApplyDiscount){
            return min( DISCOUNT_LIMIT,  (int) Math.floor(amount * DISCOUNT_RATE) );
        }
        return NO_DISCOUNT;
    }


}
