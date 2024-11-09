package store.domain.storeOpen.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import store.domain.storeOpen.FileReader;

public class StoreFileReader implements FileReader {

    private final String PRODUCT_PATH = "src/main/resources/products.md";
    private final String PROMOTIONS_PATH = "src/main/resources/promotions.md";
    private final String DELIMITER = ",";

    public List<String> getRawProducts()  {
        try {
            return read(PRODUCT_PATH);
        } catch (FileNotFoundException e) {

            return new ArrayList<>();
        }
    }

    public List<String> getRawPromotions()  {
        try {
            return read(PROMOTIONS_PATH);
        } catch (FileNotFoundException e) {

            return new ArrayList<>();
        }
    }


    private List<String> read(String filePath) throws FileNotFoundException {
        List<String> contents = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(DELIMITER);
                Collections.addAll(contents, parts); // 분리된 내용을 contents에 추가
            }
        }

        return contents;
    }

}
