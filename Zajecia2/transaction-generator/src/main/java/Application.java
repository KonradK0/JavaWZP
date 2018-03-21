import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Application {
    public static final Logger logger = LogManager.getLogger(Application.class.getName());

    public static void main(String... args) {
        TransactionGenerator transactionGenerator = new TransactionGenerator(args);

        List<String[]> nameValueList = readInputFile(transactionGenerator.getFileName());

        String outDir = transactionGenerator.getOutDir();
        createOutDir(outDir);

        long eventsCount = transactionGenerator.generateNumberOfEvents();
        for (int i = 0; i < eventsCount; i++) {
            saveJsonFile(Paths.get(outDir, "order" + i + ".json"), transactionGenerator, nameValueList, i);
        }
    }

    public static List<String[]> readInputFile(String fileName){
        Path sourceFile = Paths.get("src", "main", "resources", fileName);
        try (BufferedReader br = Files.newBufferedReader(sourceFile)) {
            logger.info("Trying to read input file of name " + sourceFile.toString());
            return br.lines().skip(1).map(s -> s.split(",")).collect(Collectors.toList());
        } catch (IOException e) {
            logger.warn("Wrong source file name");
        }
        return new ArrayList<>();
    }

    public static void createOutDir(String outDir){
        try {
            Files.createDirectory(Paths.get(outDir));
        } catch (FileAlreadyExistsException e) {
            logger.warn("This folder already exists");
        } catch (NoSuchFileException | FileNotFoundException e) {
            logger.warn("This path is incorrect");
        } catch (IOException e) {
            logger.warn("Unidentified IOException");
        }
    }

    public static void saveJsonFile(Path outPath, TransactionGenerator transactionGenerator, List <String[]> nameValueList, int index){
        try (BufferedWriter writer = Files.newBufferedWriter(outPath)) {
            logger.info("Trying to save JSON file of name " + outPath.toString());
            writer.write(createJson(transactionGenerator, nameValueList, index));
        } catch (IOException e) {
            logger.warn("File " + outPath.toString() + " already exits");
        }
    }

    private static String createJson(TransactionGenerator transactionGenerator, List<String[]> nameValueList, int id) {
        String timestamp = transactionGenerator.generateDate();
        long customerId = transactionGenerator.generateId();
        long itemsCount = transactionGenerator.generateNumberOfItems();
        ArrayList<ItemOnList> items = new ArrayList<>();
        for (int j = 0; j < itemsCount; j++) {
            String[] itemNamePrice = nameValueList.get(new Random().nextInt(nameValueList.size()));
            ItemOnList item = new ItemOnList(itemNamePrice[0], transactionGenerator.generateQuantity(), Double.valueOf(itemNamePrice[1]));
            logger.info("Creating an item on list");
            items.add(item);
        }
        double sum = computeSum(items);
        JsonObject jsonObject = new JsonObject(id, timestamp, customerId, items, sum);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(jsonObject);
    }

    private static double computeSum(List<ItemOnList> list){
        double sum = 0;
        for(ItemOnList item : list){
            sum += item.getPrice() * item.getQuantity();
        }
        return sum;
    }
}
