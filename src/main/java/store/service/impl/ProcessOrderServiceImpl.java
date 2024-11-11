package store.service.impl;

import static store.exception.ErrorMessages.NO_SAVED_INVENTORY;
import static store.exception.ErrorMessages.NO_SAVED_ORDERS;
import static store.exception.ErrorMessages.NO_SAVED_PROMOTIONS;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import store.domain.model.Orders;
import store.domain.model.Inventory;
import store.domain.model.Promotions;
import store.dto.DecisionNeededDTO;
import store.dto.InsufficientStockDTO;
import store.exception.EntityNotFoundException;
import store.domain.repository.SingleRepository;
import store.service.ProcessOrderService;

public class ProcessOrderServiceImpl implements ProcessOrderService {
    private final SingleRepository<Inventory> inventoryRepository;
    private final SingleRepository<Promotions> promotionsRepository;
    private final SingleRepository<Orders> ordersRepository;

    public ProcessOrderServiceImpl(SingleRepository<Inventory> inventoryRepository,
                                   SingleRepository<Promotions> promotionsRepository,
                                   SingleRepository<Orders> ordersRepository) {

        this.inventoryRepository = inventoryRepository;
        this.promotionsRepository = promotionsRepository;
        this.ordersRepository = ordersRepository;
    }

    @Override
    public DecisionNeededDTO getOrders(){
        List<String> ordersWithPossibleAddition = getOrdersWithPossibleAddition();
        Set<String> decisionExceptions = new HashSet<>(ordersWithPossibleAddition);
        List<InsufficientStockDTO> insufficientPromotionStocks = getInsufficientPromotionStocks(decisionExceptions);

        return new DecisionNeededDTO(ordersWithPossibleAddition, insufficientPromotionStocks);
    }


    private List<String> getOrdersWithPossibleAddition() {
        Inventory inventory =  inventoryRepository.get()
                .orElseThrow(()-> new EntityNotFoundException(NO_SAVED_INVENTORY.getErrorMessage()));
        Promotions promotions = promotionsRepository.get()
                .orElseThrow(()-> new EntityNotFoundException(NO_SAVED_PROMOTIONS.getMessage()));
        Orders orders = ordersRepository.get()
                .orElseThrow(()-> new EntityNotFoundException(NO_SAVED_ORDERS.getMessage()));
        return orders.getOrdersWithAdditionalOffer(promotions,inventory);
    }

    @Override
    public void applyAdditionDecision(Map<String, String> customerDecisions){
        Orders orders = ordersRepository.get()
                .orElseThrow(()-> new EntityNotFoundException(NO_SAVED_ORDERS.getMessage()));
        orders.applyAdditionDecision(customerDecisions);
    }


    private List<InsufficientStockDTO> getInsufficientPromotionStocks(Set<String> decisionExceptions) {
        Promotions promotions = promotionsRepository.get()
                .orElseThrow(()-> new EntityNotFoundException(NO_SAVED_PROMOTIONS.getMessage()));
        Orders orders = ordersRepository.get()
                .orElseThrow(()-> new EntityNotFoundException(NO_SAVED_ORDERS.getMessage()));

        return orders.getInsufficientPromotionStocks(promotions,decisionExceptions);
    }

    @Override
    public void applyInsufficientPromotionStock(Map<String, String> customerDecisions){
        Orders orders = ordersRepository.get()
                .orElseThrow(()-> new EntityNotFoundException(NO_SAVED_ORDERS.getMessage()));
        orders.applyInsufficientPromotionStock(customerDecisions);
    }



}
