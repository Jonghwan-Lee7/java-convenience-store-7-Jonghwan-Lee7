package store.controller;

import java.util.List;
import store.dto.StockDTO;
import store.service.StoreOpenService;
import store.service.TakeOrderService;
import store.view.InputView;
import store.view.OutputView;

public class StoreController {

    private final OutputView outputView;
    private final InputView inputView;
    private final StoreOpenService storeOpenService;
    private final TakeOrderService takeOrderService;

    public StoreController(
                           OutputView outputView,
                           InputView inputView,
                           StoreOpenService storeOpenService,
                           TakeOrderService takeOrderService
                           ) {

        this.outputView = outputView;
        this.inputView = inputView;
        this.storeOpenService = storeOpenService;
        this.takeOrderService = takeOrderService;
    }

    public void run(){
        openStore();
        takeOrder();

    }

    private void openStore(){
        storeOpenService.loadPromotions();
        storeOpenService.loadInventory();

        List<StockDTO> stockDTOs = storeOpenService.createStockDTOs();

        outputView.printStocks(stockDTOs);
    }

    private void takeOrder(){
        String rawOrder = inputView.readOrder();
        takeOrderService.takeOrder(rawOrder);
    }
}
