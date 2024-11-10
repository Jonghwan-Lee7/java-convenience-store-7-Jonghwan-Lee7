package store.utils;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import store.config.AppConfig;
import store.view.OutputView;

public class RecoveryUtils {
    private static final OutputView OUTPUT_VIEW = AppConfig.getConsoleOutputView();

    private RecoveryUtils() {
    }

    public static <T, R> R executeWithRetry(Supplier<T> inputSupplier, Function<T, R> processFunction) {
        while (true) {
            try {
                return processFunction.apply(inputSupplier.get());
            } catch (IllegalArgumentException e) {
                OUTPUT_VIEW.printError(e);
            }
        }
    }

    public static <T> void executeWithRetry(Supplier<T> inputSupplier, Consumer<T> processFunction) {
        while (true) {
            try {
                T input = inputSupplier.get();
                processFunction.accept(input);
                return;
            } catch (IllegalArgumentException e) {
                OUTPUT_VIEW.printError(e);
            }
        }
    }
}
