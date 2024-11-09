package store.utils;

import store.domain.Product;
import store.dto.StockDTO;

public class DTOMapper {

    private DTOMapper() {
    }

    public static StockDTO toStockDTO(Product product, String productName) {
        String formattedString = product.toFormattedString(productName);
        return new StockDTO(formattedString);
    }


}
