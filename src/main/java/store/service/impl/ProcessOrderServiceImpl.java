package store.service.impl;

import static store.exception.ErrorMessages.NO_SAVED_INVENTORY;
import static store.exception.ErrorMessages.NO_SAVED_ORDERS;
import static store.exception.ErrorMessages.NO_SAVED_PROMOTIONS;

import java.util.List;
import store.domain.receiveOrder.Orders;
import store.domain.storeOpen.Inventory;
import store.domain.storeOpen.Promotions;
import store.dto.PromotionStockInsufficientDTO;
import store.exception.EntityNotFoundException;
import store.repository.SingleRepository;
import store.service.ProcessOrderService;

public class ProcessOrderServiceImpl implements ProcessOrderService {
    private SingleRepository<Inventory> inventoryRepository;
    private SingleRepository<Promotions> promotionsRepository;
    private SingleRepository<Orders> ordersRepository;

    public ProcessOrderServiceImpl(SingleRepository<Inventory> inventoryRepository,
                                   SingleRepository<Promotions> promotionsRepository,
                                   SingleRepository<Orders> ordersRepository) {

        this.inventoryRepository = inventoryRepository;
        this.promotionsRepository = promotionsRepository;
        this.ordersRepository = ordersRepository;
    }


    @Override
    public List<String> getOrdersWithPossibleAddition() {
        Inventory inventory =  inventoryRepository.get()
                .orElseThrow(()-> new EntityNotFoundException(NO_SAVED_INVENTORY.getErrorMessage()));
        Promotions promotions = promotionsRepository.get()
                .orElseThrow(()-> new EntityNotFoundException(NO_SAVED_PROMOTIONS.getMessage()));
        Orders orders = ordersRepository.get()
                .orElseThrow(()-> new EntityNotFoundException(NO_SAVED_ORDERS.getMessage()));
        return orders.getOrdersWithAdditionalOffer(promotions,inventory);
    }

    @Override
    public void applyCustomerDecisionToOrders(){
        Orders orders = ordersRepository.get()
                .orElseThrow(()-> new EntityNotFoundException(NO_SAVED_ORDERS.getMessage()));
    }

    @Override
    public List<PromotionStockInsufficientDTO> getOrdersWithLackPromotionStock() {
        Inventory inventory =  inventoryRepository.get()
                .orElseThrow(()-> new EntityNotFoundException(NO_SAVED_INVENTORY.getErrorMessage()));
        Promotions promotions = promotionsRepository.get()
                .orElseThrow(()-> new EntityNotFoundException(NO_SAVED_PROMOTIONS.getMessage()));
        Orders orders = ordersRepository.get()
                .orElseThrow(()-> new EntityNotFoundException(NO_SAVED_ORDERS.getMessage()));

        return orders.getOrdersWithLackPromotionStock(promotions);
    }


}
