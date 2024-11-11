package store.utils.parser.impl;

import static store.exception.ErrorMessages.INVALID_DATE_FORMAT;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import store.utils.parser.SingleParser;

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
