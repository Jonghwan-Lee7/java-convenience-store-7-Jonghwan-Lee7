package store.view;

import java.util.List;
import store.dto.StockDTO;

public interface OutputView {
    void printStocks(List<StockDTO> stockDTOs);
}
