package store.utils;

import static store.exception.ErrorMessages.NOT_INT;
import static store.exception.ErrorMessages.NOT_POSITIVE_INT;

public class PositiveIntParser implements SingleParser<Integer> {
    public Integer parse(String rawCount){
        validate(rawCount);
        return Integer.parseInt(rawCount);
    }

    private void validate(String rawCount){
        try{
            Integer.parseInt(rawCount);
        }catch (NumberFormatException e){
            throw new IllegalArgumentException(NOT_INT.getErrorMessage());
        }
        if(Integer.parseInt(rawCount) <= 0){
            throw new IllegalArgumentException(NOT_POSITIVE_INT.getErrorMessage());
        }
    }

}
