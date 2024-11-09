package store.controller;

import java.util.List;
import store.dto.StockDTO;
import store.service.StoreOpenService;
import store.view.InputView;
import store.view.OutputView;

public class StoreController {
    private final StoreOpenService storeOpenService;
    private final OutputView outputView;
    private final InputView inputView;

    public StoreController(StoreOpenService storeOpenService,
                           OutputView outputView,
                           InputView inputView) {
        this.storeOpenService = storeOpenService;
        this.outputView = outputView;
        this.inputView = inputView;
    }

    public void run(){
        storeOpenService.loadPromotions();
        storeOpenService.loadInventory();

        List<StockDTO> stockDTOs = storeOpenService.createStockDTOs();

        outputView.printStocks(stockDTOs);

    }
}
