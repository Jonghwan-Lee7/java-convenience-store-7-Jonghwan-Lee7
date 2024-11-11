package store.utils;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static store.exception.ErrorMessages.INVALID_DATE_FORMAT;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import store.utils.parser.impl.LocalDateParser;

@DisplayName("[Utils] LocalDateParser")
public class LocalDateParserTest {
    private final LocalDateParser localDateParser = new LocalDateParser();

    @Nested
    @DisplayName("[parse] 문자열을 LocalDate로 변환한다")
    class Parse {
        @Test
        @DisplayName("[Pass] 올바른 형식의 날짜 문자열은 정상적으로 파싱된다.")
        void Given_ValidDate_When_Parse_Then_ReturnLocalDate() {
            // given
            final String VALID_DATE = "2023-11-11";

            // when & then
            assertDoesNotThrow(() -> {
                LocalDate result = localDateParser.parse(VALID_DATE);
                assertThat(result).isEqualTo(LocalDate.parse(VALID_DATE));
            });
        }

        @ParameterizedTest
        @ValueSource(strings = {"2023/11/11", "11-11-2023", "2023.11.11"}) // given
        @DisplayName("[Exception] 잘못된 형식의 날짜 문자열은 예외를 발생시킨다.")
        void Given_InvalidDateFormat_When_Parse_Then_ThrowException(String invalidDate) {
            // when & then
            assertThatThrownBy(() -> {
                LocalDate result = localDateParser.parse(invalidDate);
                assertThat(result).isNull(); // 파싱이 실패하면 null이 반환되어야 함
            }).hasMessageContaining(INVALID_DATE_FORMAT.getErrorMessage());
        }
    }
}
