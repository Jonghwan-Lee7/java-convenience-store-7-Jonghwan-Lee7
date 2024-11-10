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
    public void openStore(){
        storeOpenService.loadPromotions();
        storeOpenService.loadInventory();
    }

    public void purchase(){
        takeOrder();
        processOrder();
        finishOrder();
        decideRePurchaseOrNot();
    }


    private void takeOrder(){
        List<StockDTO> stockDTOs = storeOpenService.createStockDTOs();
        outputView.printStocks(stockDTOs);
        takeValidOrder();
    }

    private void processOrder(){
        processAddition();
        processNoPromotion();
    }


    private void finishOrder(){
        String answer = getValidMemberShipAnswer();
        String receipt = finishOrderService.getTotalReceipt(answer);
        outputView.printReceipt(receipt);
    }

    private void decideRePurchaseOrNot(){
        String answer = getValidRePurchaseDecision();
        if (answer.equals("Y")){
            purchase();
        }

    }

    private String getValidRePurchaseDecision(){
        while(true){
            try{
                String answer =  inputView.readDecisionAboutRePurchase();
                responseValidator.validate(answer);
                return answer;
            } catch (IllegalArgumentException e) {
                outputView.printError(e);
            }
        }
    }



    private void takeValidOrder(){
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


    private String getValidMemberShipAnswer(){
        while(true){
            try {
                String answer = inputView.readDecisionAboutMembership();
                responseValidator.validate(answer);
                return answer;
            } catch (IllegalArgumentException e) {
                outputView.printError(e);
            }
        }
    }


    private void processAddition() {
        List<String> possibleProductsNames = processOrderService.getOrdersWithPossibleAddition();
        if (possibleProductsNames.isEmpty()) {
            return;
        }

        Map<String, String> customerDecisions = collectAdditionDecisions(possibleProductsNames);
        processOrderService.applyAdditionDecision(customerDecisions);
    }


    private void processNoPromotion() {
        List<InsufficientStockDTO> insufficientStockDTOs = processOrderService.getInsufficientPromotionStocks();
        if (insufficientStockDTOs.isEmpty()) {
            return;
        }

        Map<String, String> customerDecisions = collectCustomerNoPromotionDecisions(insufficientStockDTOs);
        processOrderService.applyInsufficientPromotionStock(customerDecisions);
    }

    private Map<String, String> collectAdditionDecisions(List<String> possibleProductsNames) {
        Map<String, String> customerDecisions = new HashMap<>();

        for (String productName : possibleProductsNames) {
            String answer = getValidAdditionAnswer(productName);
            customerDecisions.put(productName, answer);
        }
        return customerDecisions;
    }

    private String getValidAdditionAnswer(String productName) {
        while (true)
            try{
                String answer = inputView.readChoiceAboutFreeAddition(productName);
                responseValidator.validate(answer);
                return answer;
            } catch (IllegalArgumentException e) {
                outputView.printError(e);
            }
    }


    private Map<String, String> collectCustomerNoPromotionDecisions(List<InsufficientStockDTO> insufficientStockDTOs) {
        Map<String, String> customerDecisions = new HashMap<>();

        for (InsufficientStockDTO insufficientStockDTO : insufficientStockDTOs) {
            String productName = insufficientStockDTO.productName();
            String answer = getValidNoPromotionAnswer(insufficientStockDTO);
            customerDecisions.put(productName, answer);
        }
        return customerDecisions;
    }

    private String getValidNoPromotionAnswer(InsufficientStockDTO insufficientStockDTO) {
        while (true) {
            String answer = inputView.readChoiceAboutNoPromotion(insufficientStockDTO);
            try {
                responseValidator.validate(answer);
                return answer;
            } catch (IllegalArgumentException e) {
                outputView.printError(e);
            }
        }
    }





}
