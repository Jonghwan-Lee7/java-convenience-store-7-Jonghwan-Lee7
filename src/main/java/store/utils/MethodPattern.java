package store.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import store.utils.validator.Validator;
import store.view.OutputView;

public class MethodPattern {
    private final Validator responseValidator;
    private final OutputView outputView;

    public MethodPattern(Validator responseValidator, OutputView outputView) {
        this.responseValidator = responseValidator;
        this.outputView = outputView;
    }

    public <TargetType> Map<String, String> collectDecisions(List<TargetType> items, Function<TargetType, String> nameExtractor,
                                                             Function<TargetType, String> decisionGetter) {
        Map<String, String> customerDecisions = new HashMap<>();

        for (TargetType item : items) {
            String productName = nameExtractor.apply(item);
            String answer = decisionGetter.apply(item);
            customerDecisions.put(productName, answer);
        }
        return customerDecisions;
    }

    public String getValidInput(Supplier<String> inputSupplier) {
        while (true) {
            try {
                String input = inputSupplier.get();
                responseValidator.validate(input);
                return input;

            } catch (IllegalArgumentException e) {
                outputView.printError(e);
            }
        }
    }
}
