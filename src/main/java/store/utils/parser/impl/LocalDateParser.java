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
            throw new IllegalArgumentException(INVALID_DATE_FORMAT.getErrorMessage());
        }

    }

}
