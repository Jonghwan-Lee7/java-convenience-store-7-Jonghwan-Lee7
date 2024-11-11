package store.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import store.domain.model.impl.StorePromotion;

@DisplayName("[Domain] StorePromotion")
public class PromotionTest {

    private final LocalDate startDate = LocalDate.of(2024, 11, 1);
    private final LocalDate endDate = LocalDate.of(2024, 11, 30);
    private final LocalDate invalidStartDate = LocalDate.of(2023, 11, 1);
    private final LocalDate invalidEndDate = LocalDate.of(2023, 11, 30);
    private final StorePromotion promotion = StorePromotion.of(2, 1, startDate, endDate);
    private final StorePromotion expiredPromotion = StorePromotion.of(2, 1, invalidStartDate, invalidEndDate);

    @Nested
    @DisplayName("[isActive] 프로모션의 활성 상태를 확인한다")
    class IsActiveTests {
        @Test
        @DisplayName("[Pass] 현재 날짜가 프로모션 기간 내에 있을 때 활성 상태로 반환된다.")
        void Given_CurrentDateWithinPromotionPeriod_When_IsActive_Then_ReturnTrue() {

            // when
            boolean result = promotion.isActive();
            // then
            assertThat(result).isTrue();
        }

        @Test
        @DisplayName("[Fail] 현재 날짜가 프로모션 기간 외일 때 비활성 상태로 반환된다.")
        void Given_CurrentDateOutsidePromotionPeriod_When_IsActive_Then_ReturnFalse() {

            // when
            boolean result = expiredPromotion.isActive();
            // then
            assertThat(result).isFalse();
        }
    }

    @Nested
    @DisplayName("[getApplicableItemCount] 적용 가능한 아이템 수를 계산한다")
    class GetApplicableItemCountTests {
        @Test
        @DisplayName("[Pass] 구매 수에 따라 적용 가능한 아이템 수를 반환한다.")
        void Given_PurchaseCount_When_GetApplicableItemCount_Then_ReturnCount() {
            // given
            int purchaseCount = 7;
            // when
            int result = promotion.getApplicableItemCount(purchaseCount);
            // then
            assertThat(result).isEqualTo(6);
        }
    }

    @Nested
    @DisplayName("[getFreeItemCount] 무료 아이템 수를 계산한다")
    class GetFreeItemCountTests {
        @Test
        @DisplayName("[Pass] 구매 수에 따라 무료 아이템 수를 반환한다.")
        void Given_PurchaseCount_When_GetFreeItemCount_Then_ReturnCount() {
            // given
            int purchaseCount = 7;
            // when
            int result = promotion.getFreeItemCount(purchaseCount);
            // then
            assertThat(result).isEqualTo(2);
        }
    }
}
