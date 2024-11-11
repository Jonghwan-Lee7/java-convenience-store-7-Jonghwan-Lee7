package store.controller;

import java.util.List;
import java.util.Map;
import store.constants.Answer;
import store.dto.DecisionNeededDTO;
import store.dto.InsufficientStockDTO;
import store.service.FinishOrderService;
import store.service.ProcessOrderService;
import store.utils.MethodPattern;
import store.view.InputView;
import store.view.OutputView;

public class OrderController {

    private final OutputView outputView;
    private final InputView inputView;
    private final ProcessOrderService processOrderService;
    private final FinishOrderService finishOrderService;
    private final MethodPattern methodPattern;

    public OrderController(OutputView outputView,
                           InputView inputView,
                           ProcessOrderService processOrderService,
                           FinishOrderService finishOrderService,
                           MethodPattern methodPattern) {

        this.outputView = outputView;
        this.inputView = inputView;
        this.processOrderService = processOrderService;
        this.finishOrderService = finishOrderService;
        this.methodPattern = methodPattern;
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
        return methodPattern.collectDecisions(possibleProductsNames,
                productName -> productName,
                this::getValidAdditionAnswer);
    }

    private Map<String, String> collectCustomerNoPromotionDecisions(List<InsufficientStockDTO> insufficientStockDTOs) {
        return methodPattern.collectDecisions(insufficientStockDTOs,
                InsufficientStockDTO::productName,
                this::getValidNoPromotionAnswer);
    }

    private String getValidRePurchaseDecision() {
        return methodPattern.getValidInput(inputView::readDecisionAboutRePurchase);
    }

    private String getValidMemberShipAnswer() {
        return methodPattern.getValidInput(inputView::readDecisionAboutMembership);
    }

    private String getValidAdditionAnswer(String productName) {
        return methodPattern.getValidInput(() -> inputView.readChoiceAboutFreeAddition(productName));
    }

    private String getValidNoPromotionAnswer(InsufficientStockDTO insufficientStockDTO) {
        return methodPattern.getValidInput(() -> inputView.readChoiceAboutNoPromotion(insufficientStockDTO));
    }
}
