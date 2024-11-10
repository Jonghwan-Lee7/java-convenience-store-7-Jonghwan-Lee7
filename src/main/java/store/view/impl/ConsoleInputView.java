package store.view.impl;

import camp.nextstep.edu.missionutils.Console;
import store.dto.InsufficientStockDTO;
import store.view.InputView;

public class ConsoleInputView implements InputView {
    private final String PURCHASE_NOTICE = "\n구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])";
    private final String ADDITIONAL_FREE_NOTICE = "\n현재 %s은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)";
    private final String PROMOTION_NOT_APPLIED_NOTICE = "\n현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)";
    private final String MEMBERSHIP_NOTICE = "\n멤버십 할인을 받으시겠습니까? (Y/N)";

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
    public String readChoiceAboutNoPromotion(InsufficientStockDTO stockDTO){
        String productName = stockDTO.productName();
        int quantity = stockDTO.insufficientCount();
        System.out.printf(PROMOTION_NOT_APPLIED_NOTICE, productName, quantity) ;
        return Console.readLine();
    }

    @Override
    public String readDecisionAboutMembership(){
        System.out.println(MEMBERSHIP_NOTICE);
        return Console.readLine();
    }

    @Override
    public String readDecisionAboutRePurchase(){
        System.out.println("감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)");
        return Console.readLine();
    }

}
