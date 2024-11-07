package store.view.impl;

import camp.nextstep.edu.missionutils.Console;
import store.view.InputView;

public class ConsoleInputView implements InputView {
    private final String PURCHASE_NOTICE = "구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])";
    private final String ADDITIONAL_FREE_NOTICE = "현재 %s은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)\n";
    private final String PROMOTION_NOT_APPLIED_NOTICE = "현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)\n";


    @Override
    public String readOrder() {
        System.out.println(PURCHASE_NOTICE);
        return Console.readLine();
    }

    @Override
    public String readChoiceAboutFreeAddition (String productName) {
        System.out.printf(ADDITIONAL_FREE_NOTICE, productName);
        return Console.readLine();
    }

    @Override
    public String readChoiceAboutNoPromotion(String productName, int quantity){
        System.out.printf(PROMOTION_NOT_APPLIED_NOTICE, productName, quantity) ;
        return Console.readLine();
    }

}
