package store.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import store.domain.model.Inventory;
import store.domain.model.Order;
import store.domain.model.Product;
import store.domain.model.Promotions;
import store.domain.model.impl.StoreInventory;
import store.domain.model.impl.StoreOrder;
import store.domain.model.impl.StoreOrders;
import store.domain.model.impl.StoreProduct;
import store.domain.model.impl.StorePromotion;
import store.domain.model.impl.StorePromotions;
import store.dto.FinalOrderDTO;
import store.dto.FinalPromotionDTO;
import store.dto.InsufficientStockDTO;

@DisplayName("[Domain] Orders")
public class OrdersTest {
    private List<Order> orders;
    private StoreOrders storeOrders;
    private Promotions promotions;
    private Inventory inventory;

    @BeforeEach
    public void setUp() {
        // 샘플 Order 객체 생성
        orders = new ArrayList<>();
        orders.add(StoreOrder.of("콜라", 2, 10,1000, "탄산2+1"));
        orders.add(StoreOrder.of("사이다", 5, 5,500, null));
        orders.add(StoreOrder.of("호빵", 0, 5,2000, "겨울할인"));

        // StoreOrders 객체 생성
        storeOrders = StoreOrders.of(orders);

        // Promotions 객체 생성
        String dateString1 = "2024-01-01";
        String dateString2 = "2024-12-31";

        // 문자열을 LocalDate로 변환
        LocalDate date1 = LocalDate.parse(dateString1, DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate date2 = LocalDate.parse(dateString2, DateTimeFormatter.ISO_LOCAL_DATE);
        promotions = StorePromotions.create();
        promotions.add("탄산2+1" ,StorePromotion.of(2,1, date1,date2 ));
        promotions.add("겨울할인",StorePromotion.of(2,1, date1,date2));

        // Inventory 객체 생성
        Product product0 = StoreProduct.of(1000, 10, 10, "탄산2+1");
        Product product1 = StoreProduct.of(2000, 5, 6, null );
        Product product2 = StoreProduct.of(500, 0, 10, "겨울할인" );

        Map<String, Product> products = new HashMap<>();
        products.put("콜라", product0);
        products.put("사이다", product1);
        products.put("호빵", product2);

        inventory = StoreInventory.from(products);
    }

    @Nested
    @DisplayName("[getOrdersWithAdditionalOffer] 추가 제공 옵션 테스트")
    class TestGetOrdersWithAdditionalOffer {

        @Test
        @DisplayName("[return] 무료로 추가할 수 있는 선택지가있는 상품명만 반환한다")
        public void testGetOrdersWithAdditionalOffer() {
            List<String> result = storeOrders.getOrdersWithAdditionalOffer(promotions, inventory);
            assertFalse(result.contains("콜라"));
            assert(result.contains("호빵"));
        }
    }

    @Nested
    @DisplayName("[getInsufficientPromotionStocks] 부족한 프로모션 재고 테스트")
    class TestGetInsufficientPromotionStocks {

        @Test
        @DisplayName("[return] 부족한 프로모션 재고를 올바르게 반환한다.")
        public void testGetInsufficientPromotionStocks() {
            Set<String> checkExceptions = new HashSet<>(storeOrders.getOrdersWithAdditionalOffer(promotions, inventory));

            List<InsufficientStockDTO> result = storeOrders.getInsufficientPromotionStocks(promotions,checkExceptions);
            assertEquals(1, result.size());
            assertEquals("콜라", result.getFirst().productName());
            assertEquals(3, result.getFirst().insufficientCount());
        }
    }

    @Nested
    @DisplayName("[applyInsufficientPromotionStock] 부족한 프로모션 재고 적용 테스트")
    class TestApplyInsufficientPromotionStock {

        @Test
        @DisplayName("[update] N을 입력 시 프로모션 미적용 구매목록을 재거한다.")
        public void testApplyInsufficientPromotionStock() {
            Map<String, String> customerDecisions = new HashMap<>();
            customerDecisions.put("사이다", "N");

            storeOrders.applyInsufficientPromotionStock(customerDecisions);

            assertEquals(0, orders.get(1).getRegularPriceCount());
        }
    }

    @Nested
    @DisplayName("[getFinalOrderDTOs] 최종 주문 DTOs 테스트")
    class TestGetFinalOrderDTOs {

        @Test
        @DisplayName("[return] DTO 리스트를 올바르게 반환한다.")
        public void testGetFinalOrderDTOs() {
            List<FinalOrderDTO> finalOrderDTOs = storeOrders.getFinalOrderDTOs();
            assertEquals(3, finalOrderDTOs.size());
            assertEquals("콜라", finalOrderDTOs.getFirst().productName());
            assertEquals(10, finalOrderDTOs.getFirst().promotionStockCount());
        }
    }

    @Nested
    @DisplayName("[getFinalPromotionDTOs] 최종 프로모션 DTOs 테스트")
    class TestGetFinalPromotionDTOs {


        @Test
        @DisplayName("[return] DTO 리스트들을 올바르게 반환한다.")
        public void testGetFinalPromotionDTOs() {
            Set<String> checkExceptions = new HashSet<>(storeOrders.getOrdersWithAdditionalOffer(promotions, inventory));
            storeOrders.getInsufficientPromotionStocks(promotions,checkExceptions);

            List<FinalPromotionDTO> finalPromotionDTOs = storeOrders.getFinalPromotionDTOs();
            assertEquals(2, finalPromotionDTOs.size());
            assertEquals("콜라", finalPromotionDTOs.getFirst().productName());
            assertEquals(3, finalPromotionDTOs.getFirst().freeCount());
        }
    }
}
