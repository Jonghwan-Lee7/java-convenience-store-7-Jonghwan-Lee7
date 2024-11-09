package store.service;

import java.util.List;
import store.dto.StockDTO;

public interface StoreOpenService {
    void loadInventory();
    void loadPromotions();
    List<StockDTO> createStockDTOs();
}
