package store.view.impl;

import java.util.List;
import store.dto.StockDTO;
import store.view.OutputView;

public class ConsoleOutputView implements OutputView {
    private final String WELCOME_MESSAGE = "안녕하세요. W편의점입니다.";
    private final String NOTICE_CURRENT_STOCKS = "현재 보유하고 있는 상품입니다.";

    @Override
    public void printStocks(List<StockDTO> stockDTOs){
        printWelcomeMessage();
        for (StockDTO stockDTO : stockDTOs) {
            System.out.println(stockDTO.stock());
        }
    }

    @Override
    public void printReceipt(String receipt){
        System.out.println(receipt);
        printSpace();
    }


    private void printWelcomeMessage(){
        System.out.println(WELCOME_MESSAGE);
        System.out.println(NOTICE_CURRENT_STOCKS);
        printSpace();
    }

    private void printSpace(){
        System.out.println();
    }
}
