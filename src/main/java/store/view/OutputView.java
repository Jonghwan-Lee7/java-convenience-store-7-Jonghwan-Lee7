package store.view;

import java.util.List;
import store.dto.FormattedStockDTO;

public interface OutputView {
    void printStocks(List<FormattedStockDTO> formattedStockDTOS);
    void printReceipt(String receipt);
    void printError(Exception e);
}
