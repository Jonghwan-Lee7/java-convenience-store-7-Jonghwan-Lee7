package store.domain.storeOpen;

import java.util.List;

public interface FileReader {
    List<String> getRawProducts();
    List<String> getRawPromotions();
}
