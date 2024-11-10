package store.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import store.dto.InsufficientStockDTO;
import store.dto.StockDTO;
import store.service.FinishOrderService;
import store.service.ProcessOrderService;
import store.service.StoreOpenService;
import store.service.ReceiveOrderService;
import store.utils.Validator;
import store.view.InputView;
import store.view.OutputView;

public class StoreController {

    private final OutputView outputView;
    private final InputView inputView;
    private final StoreOpenService storeOpenService;
    private final ReceiveOrderService receiveOrderService;
    private final ProcessOrderService processOrderService;
    private final FinishOrderService finishOrderService;
    private final Validator responseValidator;

    public StoreController(
                           OutputView outputView,
                           InputView inputView,
                           StoreOpenService storeOpenService,
                           ReceiveOrderService takeOrderService,
                           ProcessOrderService processOrderService,
                           FinishOrderService finishOrderService,
                           Validator responseValidator
                           ) {

        this.outputView = outputView;
        this.inputView = inputView;
        this.storeOpenService = storeOpenService;
        this.receiveOrderService = takeOrderService;
        this.processOrderService = processOrderService;
        this.finishOrderService = finishOrderService;
        this.responseValidator = responseValidator;
    }

    public void run(){
        openStore();
        takeOrder();
        processOrder();
        finishOrder();
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

    private void processOrder(){
        processAddition();
        processNoPromotion();
    }

    private void finishOrder(){
        String answer = inputView.readDecisionAboutMembership();
        responseValidator.validate(answer);
        String receipt = finishOrderService.getTotalReceipt(answer);
        outputView.printReceipt(receipt);
    }


    private void processAddition() {
        List<String> possibleProductsNames = processOrderService.getOrdersWithPossibleAddition();
        if (possibleProductsNames.isEmpty()) {
            return;
        }

        Map<String, String> customerDecisions = collectCustomerAdditionDecisions(possibleProductsNames);
        processOrderService.applyAdditionDecision(customerDecisions);
    }

    private Map<String, String> collectCustomerAdditionDecisions(List<String> possibleProductsNames) {
        Map<String, String> customerDecisions = new HashMap<>();

        for (String productName : possibleProductsNames) {
            String answer = inputView.readChoiceAboutFreeAddition(productName);
            responseValidator.validate(answer);
            customerDecisions.put(productName, answer);
        }
        return customerDecisions;
    }


    private void processNoPromotion() {
        List<InsufficientStockDTO> insufficientStockDTOs = processOrderService.getInsufficientPromotionStocks();
        if (insufficientStockDTOs.isEmpty()) {
            return;
        }

        Map<String, String> customerDecisions = collectCustomerNoPromotionDecisions(insufficientStockDTOs);
        processOrderService.applyInsufficientPromotionStock(customerDecisions);
    }

    private Map<String, String> collectCustomerNoPromotionDecisions(List<InsufficientStockDTO> insufficientStockDTOs) {
        Map<String, String> customerDecisions = new HashMap<>();

        for (InsufficientStockDTO insufficientStockDTO : insufficientStockDTOs) {
            String productName = insufficientStockDTO.productName();

            String answer = inputView.readChoiceAboutNoPromotion(insufficientStockDTO);
            responseValidator.validate(answer);
            customerDecisions.put(productName, answer);
        }
        return customerDecisions;
    }


}
