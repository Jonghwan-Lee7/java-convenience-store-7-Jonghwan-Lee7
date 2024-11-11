package store.utils;

import store.domain.model.Product;
import store.dto.FormattedStockDTO;

public class DTOMapper {

    private DTOMapper() {
    }

    public static FormattedStockDTO toStockDTO(Product product, String productName) {
        String formattedString = product.toFormattedString(productName);
        return new FormattedStockDTO(formattedString);
    }


}
