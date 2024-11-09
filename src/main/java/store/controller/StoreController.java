package store.controller;

import store.service.StoreOpenService;

public class StoreController {
    private final StoreOpenService storeOpenService;

    public StoreController(StoreOpenService storeOpenService) {
        this.storeOpenService = storeOpenService;
    }

    public void run(){
        storeOpenService.loadPromotions();
        storeOpenService.loadInventory();
    }
}
