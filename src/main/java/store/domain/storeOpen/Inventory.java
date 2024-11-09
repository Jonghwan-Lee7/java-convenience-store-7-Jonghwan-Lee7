package store.domain.storeOpen;

import java.util.List;
import store.dto.StockDTO;

public interface Inventory {
    List<StockDTO> toDTOs();

    List<Integer> getPurchaseDetails(String productName, int quantity);
}
