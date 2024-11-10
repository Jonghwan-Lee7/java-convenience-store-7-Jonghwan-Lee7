package store.domain.receiveOrder;

import java.util.List;
import store.domain.storeOpen.Inventory;
import store.domain.storeOpen.Promotions;
import store.dto.PromotionStockInsufficientDTO;

public interface Orders {
    List<Order> getOrders();
    List<String> getOrdersWithAdditionalOffer(Promotions promotions, Inventory inventory);
    List<PromotionStockInsufficientDTO> getOrdersWithLackPromotionStock(Promotions promotions);
}
