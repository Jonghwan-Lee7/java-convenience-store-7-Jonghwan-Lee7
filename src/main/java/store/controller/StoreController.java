package store.controller;

import java.util.List;
import store.dto.FormattedStockDTO;
import store.service.PrepareOrderService;
import store.service.ReceiveOrderService;
import store.view.InputView;
import store.view.OutputView;


public class StoreController {

    private final OutputView outputView;
    private final InputView inputView;
    private final PrepareOrderService prepareOrderService;
    private final ReceiveOrderService receiveOrderService;
    private final OrderController orderController;

    public StoreController(
                           OutputView outputView,
                           InputView inputView,
                           PrepareOrderService prepareOrderService,
                           ReceiveOrderService takeOrderService,
                           OrderController orderController
                           ) {

        this.outputView = outputView;
        this.inputView = inputView;
        this.prepareOrderService = prepareOrderService;
        this.receiveOrderService = takeOrderService;
        this.orderController = orderController;
    }
    public void openStore(){
        prepareOrderService.loadPromotions();
        prepareOrderService.loadInventory();
    }

    public void purchase(){
        receiveOrder();
        processOrder();
        finishOrder();
        repurchaseOrNot();
    }

    private void processOrder(){
        orderController.processOrder();
    }

    private void finishOrder(){
        orderController.finishOrder();
    }

    private void repurchaseOrNot(){
        orderController.repurchaseOrNot(this::purchase);
    }


    private void receiveOrder(){
        List<FormattedStockDTO> formattedStockDTOS = prepareOrderService.createFormattedStockDTOs();
        outputView.printStocks(formattedStockDTOS);
        receiveValidOrder();
    }


    private void receiveValidOrder(){
        while(true){
            try {
                String rawOrder = inputView.readOrder();
                receiveOrderService.takeOrder(rawOrder);
                return;
            } catch (IllegalArgumentException e) {
                outputView.printError(e);
            }
        }
    }













}
