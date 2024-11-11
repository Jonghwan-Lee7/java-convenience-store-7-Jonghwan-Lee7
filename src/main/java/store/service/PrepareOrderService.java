package store.service;

import java.util.List;
import store.dto.FormattedStockDTO;

public interface PrepareOrderService {
    void loadInventory();
    void loadPromotions();
    List<FormattedStockDTO> createFormattedStockDTOs();
}
