package store.domain;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static store.exception.ErrorMessages.INVALID_PRODUCT_NAME;
import static store.exception.ErrorMessages.INVALID_PROMOTION_NAME;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import store.domain.model.Product;
import store.domain.model.impl.StoreInventory;
import store.domain.model.impl.StoreProduct;
import store.dto.FormattedStockDTO;

@DisplayName("[Domain] Inventory")
public class InventoryTest {
    private final static int TEST_PRICE_0 = 1000;
    private final static int TEST_PRICE_1 = 2000;
    private final static int TEST_NORMAL_STOCK_0 = 5;
    private final static int TEST_NORMAL_STOCK_1 = 0;
    private final static int TEST_PROMOTION_STOCK_0 = 10;
    private final static int TEST_PROMOTION_STOCK_1 = 0;
    private final static String TEST_PRODUCT_NAME_0 = "콜라";
    private final static String TEST_PRODUCT_NAME_1 = "사이다";
    private final static String TEST_PRODUCT_NAME_2 = "호빵";
    private final static String TEST_PROMOTION_NAME_0 = "탄산2+1";
    private final static String TEST_PROMOTION_NAME_1 = null;


    private static StoreInventory storeInventory;

    @BeforeAll
    public static void setUp() {

        // Product 객체 생성
        Product product0 = StoreProduct.of(TEST_PRICE_0, TEST_NORMAL_STOCK_0, TEST_PROMOTION_STOCK_0, TEST_PROMOTION_NAME_0);
        Product product1 = StoreProduct.of(TEST_PRICE_1, TEST_NORMAL_STOCK_0, TEST_PROMOTION_STOCK_1, TEST_PROMOTION_NAME_1 );
        Product product2 = StoreProduct.of(TEST_PRICE_1, TEST_NORMAL_STOCK_1, TEST_PROMOTION_STOCK_1, TEST_PROMOTION_NAME_1 );

        // 제품 이름과 제품 객체를 포함하는 맵 생성
        Map<String, Product> products = new HashMap<>();
        products.put(TEST_PRODUCT_NAME_0, product0);
        products.put(TEST_PRODUCT_NAME_1, product1);
        products.put(TEST_PRODUCT_NAME_2, product2);

        // StoreInventory 객체 생성
        storeInventory = StoreInventory.from(products);
    }

    @Nested
    @DisplayName("[toDTOs] StockDTO 리스트 생성 기능 테스트")
    class TestToDTOs {

        List<FormattedStockDTO> formattedStockDTOS = storeInventory.toDTOs();

        @Test
        @DisplayName("[return] 프로모션 재고와 일반 재고를 나누어서 올바르게 반환한다.")
        public void testWithPromotionStocks() {
            String validReturn0 ="- 콜라 1,000원 10개 탄산2+1\n- 콜라 1,000원 5개";
            assertEquals(validReturn0, formattedStockDTOS.getLast().stock());
        }

        @Test
        @DisplayName("[return] 프로모션 재고가 없는 경우 일반 재고만 반환한다")
        public void testWithoutPromotionStocks() {
            String validReturn ="- 사이다 2,000원 5개";
            assertEquals(validReturn, formattedStockDTOS.getFirst().stock());
        }

        @Test
        @DisplayName("[return] 재고가 0개인 경우 재고 없음 이라 반환한다")
        public void testWithNoStock() {
            String validReturn ="- 호빵 2,000원 재고 없음";
            assertEquals(validReturn, formattedStockDTOS.get(1).stock());
        }
    }

    @Nested
    @DisplayName("[getPurchaseDetails] 구매 세부 정보 기능 테스트")
    class TestGetPurchaseDetails {

        @Test
        @DisplayName("[return] 정상적으로 구매 세부 정보를 반환한다.")
        public void testValidPurchaseDetails() {
            List<Integer> purchaseDetails = storeInventory.getPurchaseDetails(TEST_PRODUCT_NAME_0, 3);
            assertNotNull(purchaseDetails);
            assertEquals(2, purchaseDetails.size()); // 예시
        }

        @Test
        @DisplayName("[exception] 유효하지 않은 제품명으로 구매 세부 정보를 요청할 때 예외가 발생한다.")
        public void testInvalidProductName() {
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                storeInventory.getPurchaseDetails("Invalid Product", 2);
            });
            assertEquals(INVALID_PRODUCT_NAME.getErrorMessage(), exception.getMessage());
        }
    }

    @Nested
    @DisplayName("[getPromotionName] 프로모션 이름 기능 테스트")
    class TestGetPromotionName {

        @Test
        @DisplayName("[return] 유효한 제품에 대해 프로모션 이름이 반환된다.")
        public void testValidPromotionName() {
            String promotionName = storeInventory.getPromotionName(TEST_PRODUCT_NAME_0);
            assertEquals(TEST_PROMOTION_NAME_0, promotionName);
        }

        @Test
        @DisplayName("[return] 프로모션 이름이 없는 경우 null이 반환된다.")
        public void testNoPromotionName() {
            String promotionName = storeInventory.getPromotionName(TEST_PRODUCT_NAME_1);
            assertNull(promotionName);
        }
    }

    @Nested
    @DisplayName("[hasEnoughPromotionStock] 프로모션 재고 확인 기능 테스트")
    class TestHasEnoughPromotionStock {

        @Test
        @DisplayName("[return] 충분한 프로모션 재고가 있는 경우 true를 반환한다.")
        public void testHasEnoughPromotionStock() {
            boolean hasEnough = storeInventory.hasEnoughPromotionStock(TEST_PRODUCT_NAME_0, 5);
            assertTrue(hasEnough);
        }

        @Test
        @DisplayName("[return] 프로모션 재고가 부족한 경우 false를 반환한다.")
        public void testNotEnoughPromotionStock() {
            boolean hasEnough = storeInventory.hasEnoughPromotionStock(TEST_PRODUCT_NAME_1, 1);
            assertFalse(hasEnough);
        }

        @Test
        @DisplayName("[exception] 유효하지 않은 제품명으로 프로모션 재고 확인 시 예외가 발생한다.")
        public void testInvalidProductForPromotionStock() {
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                storeInventory.hasEnoughPromotionStock("Invalid Product", 1);
            });
            assertEquals(INVALID_PRODUCT_NAME.getErrorMessage(), exception.getMessage());
        }
    }


    @Nested
    @DisplayName("[validateProductPromotion] 프로모션 유효성 검사 기능 테스트")
    class TestValidateProductPromotion {

        @Test
        @DisplayName("[valid] 유효한 프로모션 세트에 대해 예외가 발생하지 않는다.")
        public void testValidPromotion() {
            Set<String> promotions = new HashSet<>(Collections.singletonList(TEST_PROMOTION_NAME_0));
            assertDoesNotThrow(() -> storeInventory.validateProductPromotion(promotions));
        }

        @Test
        @DisplayName("[exception] 유효하지 않은 프로모션 세트에 대해 예외가 발생한다.")
        public void testInvalidPromotion() {
            Set<String> promotions = new HashSet<>(Collections.singletonList("Invalid Promotion"));
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                storeInventory.validateProductPromotion(promotions);
            });
            assertEquals(INVALID_PROMOTION_NAME.getErrorMessage(), exception.getMessage());
        }
    }



}
