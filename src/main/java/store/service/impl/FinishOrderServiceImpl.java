package store.service.impl;

import static store.exception.ErrorMessages.NO_SAVED_ORDERS;

import java.util.List;
import store.domain.processOrder.Calculator;
import store.domain.processOrder.Membership;
import store.domain.processOrder.impl.StoreMembership;
import store.domain.receiveOrder.Orders;
import store.domain.storeOpen.Inventory;
import store.dto.FinalOrderDTO;
import store.dto.FinalPromotionDTO;
import store.exception.EntityNotFoundException;
import store.repository.SingleRepository;
import store.service.FinishOrderService;

public class FinishOrderServiceImpl implements FinishOrderService {
    private final SingleRepository<Orders> ordersRepository;
    private final SingleRepository<Inventory> inventoryRepository;
    private final Calculator moneyCalculator;

    private static final String RECEIPT_LOGO = "===========W 편의점=============\n";
    private static final String PRODUCT_HEADER_FORMAT = "%-10s %5s %10s\n";
    private static final String PRODUCT_ITEM_FORMAT = "%-10s %5d %10d\n";
    private static final String SUMMARY_FORMAT = "%-10s %5s %10d\n";
    private static final String PROMOTION_FORMAT = "%-10s %5s\n";
    private static final String PROMOTION_HEADER = "===========증\t정=============\n";
    private static final String SUMMARY_HEADER = "==============================\n";

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

        return receipt.toString();
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
        receipt.append(String.format(PRODUCT_HEADER_FORMAT, "상품명","수량","금액"));

        for (FinalOrderDTO finalOrderDTO : finalOrderDTOs) {
            String productName = finalOrderDTO.productName();
            int totalStock = finalOrderDTO.normalStockCount() + finalOrderDTO.promotionStockCount();
            int totalPrice =  totalStock * finalOrderDTO.price();
            receipt.append(String.format(PRODUCT_ITEM_FORMAT, productName, totalPrice, totalPrice));
        }
    }

    private void addPromotionSection(StringBuilder receipt, List<FinalPromotionDTO> finalPromotionDTOS){
        receipt.append(PROMOTION_HEADER);

        for(FinalPromotionDTO finalPromotionDTO : finalPromotionDTOS){
            String productName = finalPromotionDTO.productName();
            String count = String.valueOf(finalPromotionDTO.freeCount());
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
        appendTotalPurchase(receipt, totalCounts, totalPurchaseAmount);
        receipt.append(String.format(SUMMARY_FORMAT, "행사할인", "", (-1) * totalPromotionAmount));
        receipt.append(String.format(SUMMARY_FORMAT, "멤버십할인", "", (-1) * membershipDiscountAmount));
        receipt.append(String.format(SUMMARY_FORMAT, "내실돈", "", amountToPay));
    }

    private void appendTotalPurchase(StringBuilder totalSummary, int totalCounts, int totalPurchaseAmount) {
        totalSummary.append(String.format(PRODUCT_HEADER_FORMAT, "총구매액", totalCounts, totalPurchaseAmount));
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
        return totalPurchaseAmount - totalPromotionAmount - membershipDiscountAmount;
    }

    private int getMembershipDiscountAmount(String answer, int totalPurchaseAmount, int promotionAppliedAmount){
        Membership storeMembership = StoreMembership.create(answer);
        return storeMembership.getDiscountAmount(totalPurchaseAmount,promotionAppliedAmount);
    }

}
