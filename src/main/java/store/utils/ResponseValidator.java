package store.utils;

import static store.exception.ErrorMessages.WRONG_INPUT;

public class ResponseValidator implements Validator {
    @Override
    public void validate(String response) {
        if (response.equals("Y") || response.equals("N") ){
            return;
        }
        throw new IllegalArgumentException(WRONG_INPUT.getErrorMessage());
    }
}
