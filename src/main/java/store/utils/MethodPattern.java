package store.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import store.view.OutputView;

public class MethodPattern {
    private final Validator responseValidator;
    private final OutputView outputView;

    public MethodPattern(Validator responseValidator, OutputView outputView) {
        this.responseValidator = responseValidator;
        this.outputView = outputView;
    }

    public <T> Map<String, String> collectDecisions(List<T> items, Function<T, String> nameExtractor,
                                                     Function<T, String> decisionGetter) {
        Map<String, String> customerDecisions = new HashMap<>();

        for (T item : items) {
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
