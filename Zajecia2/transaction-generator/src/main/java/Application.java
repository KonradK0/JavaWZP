import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Application {

    public static void main(String[] args) {
        TransactionGenerator transactionGenerator = new TransactionGenerator(args);

        List<String[]> nameValueList = readInputFile(transactionGenerator.getFileName());

        Path outDir = Paths.get(transactionGenerator.getOutDir());
        createOutDir(outDir);

        long eventsCount = transactionGenerator.generateNumberOfEvents();
        for (int i = 0; i < eventsCount; i++) {
            saveJsonFile(Paths.get(outDir.toString(), "order" + i + ".json"), transactionGenerator, nameValueList, i);
        }
    }

    private static List<String[]> readInputFile(String fileName){
        Path sourceFile = Paths.get("src", "main", "resources", fileName);
        try (BufferedReader br = Files.newBufferedReader(sourceFile)) {
            return br.lines().skip(1).map(s -> s.split(",")).collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println("Wrong source file name");
        }
        return new ArrayList<>();
    }

    private static void createOutDir(Path outDir){
        try {
            Files.createDirectory(outDir);
        } catch (FileAlreadyExistsException e) {
            System.out.println("This folder already exists");
        } catch (FileNotFoundException e) {
            System.out.println("This path is incorrect");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveJsonFile(Path outPath, TransactionGenerator transactionGenerator, List <String[]> nameValueList, int index){
        try (BufferedWriter writer = Files.newBufferedWriter(outPath)) {
            writer.write(createJson(transactionGenerator, nameValueList, index));
        } catch (IOException e) {
            System.out.println("File " + outPath.toString() + " already exits");
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