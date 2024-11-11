package store.config;

import java.time.LocalDate;
import store.controller.OrderController;
import store.controller.StoreController;
import store.domain.builder.TwoInputsBuilder;
import store.domain.builder.impl.OrdersBuilder;
import store.domain.processDiscount.Calculator;
import store.domain.processDiscount.impl.MoneyCalculator;
import store.domain.model.Orders;
import store.domain.model.Inventory;
import store.domain.model.Promotions;
import store.domain.builder.InputBuilder;
import store.domain.builder.impl.InventoryBuilder;
import store.domain.builder.impl.PromotionsBuilder;
import store.domain.repository.impl.OrdersRepository;
import store.domain.repository.SingleRepository;
import store.domain.repository.impl.InventoryRepository;
import store.domain.repository.impl.PromotionsRepository;
import store.service.FinishOrderService;
import store.service.ProcessOrderService;
import store.service.PrepareOrderService;
import store.service.ReceiveOrderService;
import store.service.impl.FinishOrderServiceImpl;
import store.service.impl.ProcessOrderServiceImpl;
import store.service.impl.PrepareOrderServiceImpl;
import store.service.impl.ReceiveOrderServiceImpl;
import store.utils.LocalDateParser;
import store.utils.MethodPattern;
import store.utils.ResponseValidator;
import store.utils.SingleParser;
import store.utils.PositiveIntParser;
import store.utils.Validator;
import store.view.InputView;
import store.view.OutputView;
import store.view.impl.ConsoleInputView;
import store.view.impl.ConsoleOutputView;

public class AppConfig {

    private final static OutputView consoleOutputView = new ConsoleOutputView();
    private final static InputView consoleInputView = new ConsoleInputView();

    private final static SingleRepository<Inventory> inventoryRepository = new InventoryRepository();
    private final static SingleRepository<Promotions> promotionsRepository = new PromotionsRepository();
    private final static SingleRepository<Orders> ordersRepository = new OrdersRepository();

    private final static SingleParser<Integer> positiveIntParser = new PositiveIntParser();
    private final static SingleParser<LocalDate> localDateParser = new LocalDateParser();

    private final static Validator responseValidator = new ResponseValidator();

    private final static InputBuilder<Promotions> promotionsBuilder = new PromotionsBuilder(positiveIntParser, localDateParser);
    private final static InputBuilder<Inventory> inventoryBuilder = new InventoryBuilder(positiveIntParser);
    private final static TwoInputsBuilder<Orders,Inventory> ordersBuilder = new OrdersBuilder();

    private final static Calculator moneyCalculator = new MoneyCalculator();

    private final static PrepareOrderService prepareOrderService = new PrepareOrderServiceImpl(
            promotionsRepository,
            inventoryRepository,
            promotionsBuilder,
            inventoryBuilder);
    private final static ReceiveOrderService receiveOrderService = new ReceiveOrderServiceImpl(ordersBuilder,inventoryRepository,ordersRepository);
    private final static ProcessOrderService processOrderService = new ProcessOrderServiceImpl(inventoryRepository, promotionsRepository, ordersRepository);
    private final static FinishOrderService finishOrderService = new FinishOrderServiceImpl(ordersRepository,inventoryRepository,moneyCalculator);

    private final static MethodPattern methodPattern = new MethodPattern(responseValidator,consoleOutputView);

    private final static OrderController orderController = new OrderController(consoleOutputView,consoleInputView,processOrderService,finishOrderService,methodPattern);

    private final static StoreController storeController = new StoreController(consoleOutputView, consoleInputView,
            prepareOrderService,receiveOrderService,orderController);

    public static StoreController getStoreController(){
        return storeController;
    }


}
