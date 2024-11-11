package store.utils;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static store.exception.ErrorMessages.NOT_INT;
import static store.exception.ErrorMessages.NOT_POSITIVE_INT;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import store.utils.parser.impl.PositiveIntParser;

public class PositiveIntParserTest {
    private final PositiveIntParser positiveIntParser = new PositiveIntParser();

    @Nested
    @DisplayName("[parse] 문자열을 양의 정수로 변환한다")
    class Parse {
        @Test
        @DisplayName("[Pass] 올바른 양의 정수 문자열은 정상적으로 파싱된다.")
        void Given_ValidPositiveInt_When_Parse_Then_ReturnInteger() {
            // given
            final String VALID_POSITIVE_INT = "5";

            // when & then
            assertDoesNotThrow(() -> {
                Integer result = positiveIntParser.parse(VALID_POSITIVE_INT);
                assertThat(result).isEqualTo(Integer.parseInt(VALID_POSITIVE_INT));
            });
        }

        @ParameterizedTest
        @ValueSource(strings = {"-1", "0"}) // given
        @DisplayName("[Exception] 0 이하의 숫자는 예외를 발생시킨다.")
        void Given_NonPositiveInt_When_Parse_Then_ThrowException(String nonPositiveInt) {
            // when & then
            assertThatThrownBy(() -> positiveIntParser.parse(nonPositiveInt))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(NOT_POSITIVE_INT.getErrorMessage());
        }

        @ParameterizedTest
        @ValueSource(strings = {"abc", "12a", "3.14"}) // given
        @DisplayName("[Exception] 정수가 아닌 문자열은 예외를 발생시킨다.")
        void Given_NonIntegerString_When_Parse_Then_ThrowException(String invalidInput) {
            // when & then
            assertThatThrownBy(() -> positiveIntParser.parse(invalidInput))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(NOT_INT.getErrorMessage());
        }
    }
}
