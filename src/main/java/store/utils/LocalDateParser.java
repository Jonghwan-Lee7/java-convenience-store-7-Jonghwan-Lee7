package store.utils;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class LocalDateParser implements Parser<LocalDate> {

    public LocalDate parse(String rawDate){
        try {
            return LocalDate.parse(rawDate);
        } catch (DateTimeParseException e) {
            System.out.println("날짜 형식이 옳바르지 않습니다");
        }

        return null;
    }

}
