package store.service.impl;

import static store.exception.ErrorMessages.NO_SAVED_ORDERS;

import java.util.List;
import store.domain.processDiscount.Calculator;
import store.domain.processDiscount.Membership;
import store.domain.processDiscount.impl.StoreMembership;
import store.domain.model.Orders;
import store.domain.model.Inventory;
import store.dto.FinalOrderDTO;
import store.dto.FinalPromotionDTO;
import store.exception.EntityNotFoundException;
import store.domain.repository.SingleRepository;
import store.service.FinishOrderService;

public class FinishOrderServiceImpl implements FinishOrderService {
    private final SingleRepository<Orders> ordersRepository;
    private final SingleRepository<Inventory> inventoryRepository;
    private final Calculator moneyCalculator;

    private static final String PROMOTION_DISCOUNT = "행사할인";
    private static final String MEMBERSHIP_DISCOUNT = "멤버십할인";
    private static final String PRODUCT_NAME = "상품명";
    private static final String COUNT = "수량";
    private static final String PRICE = "금액";
    private static final String AMOUNT_TO_PAY = "내실돈";
    private static final String TOTAL_PURCHASE_AMOUNT = "총구매액";

    private static final String RECEIPT_LOGO = "==============W 편의점================\n";
    private static final String PROMOTION_HEADER="=============증     정===============\n";
    private static final String SUMMARY_HEADER = "====================================\n";

    private static final String NUMBER_COMMA_FORMAT = "%,d";
    private static final String PRODUCT_ITEM_FORMAT = "%-14s %5s %14s\n";
    private static final String PROMOTION_FORMAT = "%-14s %5s\n";

    private static final String EMPTY = "";
    private static final int FOR_MINUS_SIGN = -1;

    public FinishOrderServiceImpl(SingleRepository<Orders> ordersRepository,
                                  SingleRepository<Inventory> inventoryRepository,
                                  Calculator moneyCalculator) {

        this.ordersRepository = ordersRepository;
        this.inventoryRepository = inventoryRepository;
        this.moneyCalculator = moneyCalculator;

    }


    @Override
    public String getTotalReceipt(String answer){
        Orders orders = ordersRepository.get()
                .orElseThrow(()-> new EntityNotFoundException(NO_SAVED_ORDERS.getErrorMessage()));

        List<FinalOrderDTO>  finalOrderDTOs = orders.getFinalOrderDTOs();
        List<FinalPromotionDTO> finalPromotionDTOS = orders.getFinalPromotionDTOs();
        StringBuilder receipt =  makeReceipt(answer, finalOrderDTOs, finalPromotionDTOS);
        updateInventory(finalOrderDTOs);

        return receipt.toString();
    }

    private void updateInventory(List<FinalOrderDTO> finalOrderDTOs) {
        Inventory inventory =  inventoryRepository.get()
                .orElseThrow(()-> new EntityNotFoundException(NO_SAVED_ORDERS.getErrorMessage()));
        inventory.updateStocks(finalOrderDTOs);
    }

    private StringBuilder makeReceipt(String answer,
                                      List<FinalOrderDTO>  finalOrderDTOs,
                                      List<FinalPromotionDTO> finalPromotionDTOs){

        StringBuilder receipt = new StringBuilder();
        receipt.append(RECEIPT_LOGO);
        addProductList(receipt,finalOrderDTOs);
        addPromotionSection(receipt,finalPromotionDTOs);
        generateTotalSummary(receipt,answer, finalOrderDTOs, finalPromotionDTOs);

        return receipt;
    }

    private void addProductList(StringBuilder receipt, List<FinalOrderDTO>  finalOrderDTOs){
        receipt.append(String.format(PRODUCT_ITEM_FORMAT, PRODUCT_NAME,COUNT,PRICE));

        for (FinalOrderDTO finalOrderDTO : finalOrderDTOs) {
            String productName = finalOrderDTO.productName();
            String totalStock = String.format(NUMBER_COMMA_FORMAT,finalOrderDTO.normalStockCount() + finalOrderDTO.promotionStockCount());
            String totalPrice = String.format(NUMBER_COMMA_FORMAT,finalOrderDTO.price());
            receipt.append(String.format(PRODUCT_ITEM_FORMAT, productName, totalStock, totalPrice));
        }
    }

    private void addPromotionSection(StringBuilder receipt, List<FinalPromotionDTO> finalPromotionDTOS){
        receipt.append(PROMOTION_HEADER);

        for(FinalPromotionDTO finalPromotionDTO : finalPromotionDTOS){
            String productName = finalPromotionDTO.productName();
            String count = String.format(NUMBER_COMMA_FORMAT,finalPromotionDTO.freeCount());
            receipt.append(String.format(PROMOTION_FORMAT, productName, count));
        }
    }

    private void generateTotalSummary(StringBuilder receipt,
                                               String answer,
                                               List<FinalOrderDTO> finalOrderDTOs,
                                               List<FinalPromotionDTO> finalPromotionDTOs) {

        int totalCounts = calculateTotalCounts(finalOrderDTOs);
        int totalPurchaseAmount = calculateTotalPurchaseAmount(finalOrderDTOs);
        int totalPromotionAmount = calculateTotalPromotionAmount(finalPromotionDTOs);
        int membershipDiscountAmount = calculateMembershipDiscount(answer, totalPurchaseAmount, finalPromotionDTOs);
        int amountToPay = calculateAmountToPay(totalPurchaseAmount, totalPromotionAmount, membershipDiscountAmount);

        appendSummaryDetails(receipt, totalCounts, totalPurchaseAmount, totalPromotionAmount, membershipDiscountAmount, amountToPay);
    }

    private void appendSummaryDetails(StringBuilder receipt, int totalCounts, int totalPurchaseAmount,
                                      int totalPromotionAmount, int membershipDiscountAmount, int amountToPay) {

        receipt.append(SUMMARY_HEADER);
        receipt.append(String.format(PRODUCT_ITEM_FORMAT, TOTAL_PURCHASE_AMOUNT, String.format(NUMBER_COMMA_FORMAT,totalCounts), String.format(NUMBER_COMMA_FORMAT,totalPurchaseAmount)));
        receipt.append(String.format(PRODUCT_ITEM_FORMAT, PROMOTION_DISCOUNT, EMPTY, String.format(NUMBER_COMMA_FORMAT, FOR_MINUS_SIGN * totalPromotionAmount)));
        receipt.append(String.format(PRODUCT_ITEM_FORMAT, MEMBERSHIP_DISCOUNT, EMPTY, String.format(NUMBER_COMMA_FORMAT, FOR_MINUS_SIGN * membershipDiscountAmount)));
        receipt.append(String.format(PRODUCT_ITEM_FORMAT, AMOUNT_TO_PAY, EMPTY, String.format(NUMBER_COMMA_FORMAT,amountToPay)));
    }

    private int calculateTotalCounts(List<FinalOrderDTO> finalOrderDTOs) {
        return moneyCalculator.calculateTotalCounts(finalOrderDTOs);
    }

    private int calculateTotalPurchaseAmount(List<FinalOrderDTO> finalOrderDTOs) {
        return moneyCalculator.calculateTotalPurchaseAmount(finalOrderDTOs);
    }

    private int calculateTotalPromotionAmount(List<FinalPromotionDTO> finalPromotionDTOs) {
        return moneyCalculator.calculateTotalPromotionAmount(finalPromotionDTOs);
    }

    private int calculateMembershipDiscount(String answer, int totalPurchaseAmount, List<FinalPromotionDTO> finalPromotionDTOs) {
        int promotionAppliedAmount = moneyCalculator.calculatePromotionAppliedAmount(finalPromotionDTOs);
        return getMembershipDiscountAmount(answer, totalPurchaseAmount, promotionAppliedAmount);
    }

    private int calculateAmountToPay(int totalPurchaseAmount, int totalPromotionAmount, int membershipDiscountAmount) {
        return moneyCalculator.calculateAmountToPay(totalPurchaseAmount, totalPromotionAmount, membershipDiscountAmount);
    }

    private int getMembershipDiscountAmount(String answer, int totalPurchaseAmount, int promotionAppliedAmount){
        Membership storeMembership = StoreMembership.create(answer);
        return storeMembership.getDiscountAmount(totalPurchaseAmount,promotionAppliedAmount);
    }

}
