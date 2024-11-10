package store.utils;

import static store.exception.ErrorMessages.INVALID_DATE_FORMAT;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class LocalDateParser implements SingleParser<LocalDate> {

    public LocalDate parse(String rawDate){
        try {
            return LocalDate.parse(rawDate);
        } catch (DateTimeParseException e) {
            System.out.println(INVALID_DATE_FORMAT.getErrorMessage());
        }

        return null;
    }

}
