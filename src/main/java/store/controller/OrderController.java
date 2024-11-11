package store.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import store.constants.Answer;
import store.dto.DecisionNeededDTO;
import store.dto.InsufficientStockDTO;
import store.service.FinishOrderService;
import store.service.ProcessOrderService;
import store.utils.Validator;
import store.view.InputView;
import store.view.OutputView;

public class OrderController {

    private final OutputView outputView;
    private final InputView inputView;
    private final ProcessOrderService processOrderService;
    private final FinishOrderService finishOrderService;
    private final Validator responseValidator;

    public OrderController(OutputView outputView,
                           InputView inputView,
                           ProcessOrderService processOrderService,
                           FinishOrderService finishOrderService,
                           Validator responseValidator) {

        this.outputView = outputView;
        this.inputView = inputView;
        this.processOrderService = processOrderService;
        this.finishOrderService = finishOrderService;
        this.responseValidator = responseValidator;
    }


    public void processOrder(){
        DecisionNeededDTO decisionNeededDTO =  processOrderService.getOrders();
        List<String> ordersWithPossibleAddition = decisionNeededDTO.OrdersWithPossibleAddition();
        List<InsufficientStockDTO> insufficientStockDTOs = decisionNeededDTO.insufficientPromotionStocks();


        processAddition(ordersWithPossibleAddition);
        processNoPromotion(insufficientStockDTOs);
    }


    public void finishOrder(){
        String answer = getValidMemberShipAnswer();
        String receipt = finishOrderService.getTotalReceipt(answer);
        outputView.printReceipt(receipt);
    }

    public void repurchaseOrNot(Runnable methodToRun) {
        String answer = getValidRePurchaseDecision();
        if (answer.equals(Answer.YES.getMessage())) {
            methodToRun.run();
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
