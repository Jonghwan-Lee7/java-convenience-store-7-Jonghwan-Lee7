package store.service;

import java.util.List;
import store.dto.StockDTO;

public interface PrepareOrderService {
    void loadInventory();
    void loadPromotions();
    List<StockDTO> createStockDTOs();
}
