package store.domain.processOrder.impl;

import static java.lang.Math.min;

import store.domain.processOrder.Membership;

public class StoreMembership implements Membership {
    private final int DISCOUNT_LIMIT = 8000;
    private final double DISCOUNT_RATE = 0.3;

    @Override
    public int getDiscountAmount(int amount){
        return min( DISCOUNT_LIMIT,  (int) Math.floor(amount * DISCOUNT_RATE) );
    }
}
