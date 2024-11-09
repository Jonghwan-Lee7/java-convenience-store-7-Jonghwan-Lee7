package store.domain.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import store.domain.FileReader;

public class StoreFileReader implements FileReader {

    private final String PRODUCT_PATH = "src/main/resources/products.md";
    private final String PROMOTIONS_PATH = "src/main/resources/promotions.md";

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
                contents.add(scanner.nextLine());
            }
        }

        return contents;
    }

}
