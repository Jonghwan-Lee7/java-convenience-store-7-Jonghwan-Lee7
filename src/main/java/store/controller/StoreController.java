package store.controller;

import java.util.List;
import store.dto.StockDTO;
import store.service.StoreOpenService;
import store.service.ReceiveOrderService;
import store.view.InputView;
import store.view.OutputView;

public class StoreController {

    private final OutputView outputView;
    private final InputView inputView;
    private final StoreOpenService storeOpenService;
    private final ReceiveOrderService receiveOrderService;

    public StoreController(
                           OutputView outputView,
                           InputView inputView,
                           StoreOpenService storeOpenService,
                           ReceiveOrderService takeOrderService
                           ) {

        this.outputView = outputView;
        this.inputView = inputView;
        this.storeOpenService = storeOpenService;
        this.receiveOrderService = takeOrderService;
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
        receiveOrderService.takeOrder(rawOrder);
    }
}
