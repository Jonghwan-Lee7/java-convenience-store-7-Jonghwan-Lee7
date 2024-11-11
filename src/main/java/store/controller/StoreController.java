package store.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import store.constants.Answer;
import store.dto.DecisionNeededDTO;
import store.dto.InsufficientStockDTO;
import store.dto.StockDTO;
import store.service.FinishOrderService;
import store.service.ProcessOrderService;
import store.service.PrepareOrderService;
import store.service.ReceiveOrderService;
import store.utils.Validator;
import store.view.InputView;
import store.view.OutputView;


public class StoreController {

    private final OutputView outputView;
    private final InputView inputView;
    private final PrepareOrderService prepareOrderService;
    private final ReceiveOrderService receiveOrderService;
    private final ProcessOrderService processOrderService;
    private final FinishOrderService finishOrderService;
    private final Validator responseValidator;

    public StoreController(
                           OutputView outputView,
                           InputView inputView,
                           PrepareOrderService prepareOrderService,
                           ReceiveOrderService takeOrderService,
                           ProcessOrderService processOrderService,
                           FinishOrderService finishOrderService,
                           Validator responseValidator
                           ) {

        this.outputView = outputView;
        this.inputView = inputView;
        this.prepareOrderService = prepareOrderService;
        this.receiveOrderService = takeOrderService;
        this.processOrderService = processOrderService;
        this.finishOrderService = finishOrderService;
        this.responseValidator = responseValidator;
    }
    public void openStore(){
        prepareOrderService.loadPromotions();
        prepareOrderService.loadInventory();
    }

    public void purchase(){
        takeOrder();
        processOrder();
        finishOrder();
        decideRePurchaseOrNot();
    }


    private void takeOrder(){
        List<StockDTO> stockDTOs = prepareOrderService.createStockDTOs();
        outputView.printStocks(stockDTOs);
        takeValidOrder();
    }

    private void processOrder(){
        DecisionNeededDTO decisionNeededDTO =  processOrderService.getOrders();
        List<String> ordersWithPossibleAddition = decisionNeededDTO.OrdersWithPossibleAddition();
        List<InsufficientStockDTO> insufficientStockDTOs = decisionNeededDTO.insufficientPromotionStocks();


        processAddition(ordersWithPossibleAddition);
        processNoPromotion(insufficientStockDTOs);
    }


    private void finishOrder(){
        String answer = getValidMemberShipAnswer();
        String receipt = finishOrderService.getTotalReceipt(answer);
        outputView.printReceipt(receipt);
    }

    private void decideRePurchaseOrNot(){
        String answer = getValidRePurchaseDecision();
        if (answer.equals(Answer.YES.getMessage())){
            purchase();
        }

    }


    private void processAddition(List<String> ordersWithPossibleAddition) {
        if (ordersWithPossibleAddition.isEmpty()) {
            return;
        }

        Map<String, String> customerDecisions = collectAdditionDecisions(ordersWithPossibleAddition);
        processOrderService.applyAdditionDecision(customerDecisions);
    }


    private void processNoPromotion(List<InsufficientStockDTO> insufficientStockDTOs) {
        if (insufficientStockDTOs.isEmpty()) {
            return;
        }

        Map<String, String> customerDecisions = collectCustomerNoPromotionDecisions(insufficientStockDTOs);
        processOrderService.applyInsufficientPromotionStock(customerDecisions);
    }

    private Map<String, String> collectAdditionDecisions(List<String> possibleProductsNames) {
        return collectDecisions(possibleProductsNames,
                productName -> productName,
                this::getValidAdditionAnswer);
    }

    private Map<String, String> collectCustomerNoPromotionDecisions(List<InsufficientStockDTO> insufficientStockDTOs) {
        return collectDecisions(insufficientStockDTOs,
                InsufficientStockDTO::productName,
                this::getValidNoPromotionAnswer);
    }



    private <T> Map<String, String> collectDecisions(List<T> items, Function<T, String> nameExtractor,
                                                     Function<T, String> decisionGetter) {
        Map<String, String> customerDecisions = new HashMap<>();

        for (T item : items) {
            String productName = nameExtractor.apply(item);
            String answer = decisionGetter.apply(item);
            customerDecisions.put(productName, answer);
        }
        return customerDecisions;
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


    private String getValidInput(Supplier<String> inputSupplier) {
        while (true) {
            try {
                String input = inputSupplier.get();
                responseValidator.validate(input);
                return input;

            } catch (IllegalArgumentException e) {
                outputView.printError(e);
            }
        }
    }

    private String getValidRePurchaseDecision() {
        return getValidInput(inputView::readDecisionAboutRePurchase);
    }

    private String getValidMemberShipAnswer() {
        return getValidInput(inputView::readDecisionAboutMembership);
    }

    private String getValidAdditionAnswer(String productName) {
        return getValidInput(() -> inputView.readChoiceAboutFreeAddition(productName));
    }

    private String getValidNoPromotionAnswer(InsufficientStockDTO insufficientStockDTO) {
        return getValidInput(() -> inputView.readChoiceAboutNoPromotion(insufficientStockDTO));
    }










}
