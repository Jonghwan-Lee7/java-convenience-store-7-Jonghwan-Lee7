package store.utils.validator.impl;

import static store.exception.ErrorMessages.WRONG_INPUT;

import store.constants.Answer;
import store.utils.validator.Validator;

public class ResponseValidator implements Validator {
    @Override
    public void validate(String response) {
        if (response.equals(Answer.YES.getMessage()) || response.equals(Answer.NO.getMessage()) ) {
            return;
        }
        throw new IllegalArgumentException(WRONG_INPUT.getErrorMessage());
    }
}
