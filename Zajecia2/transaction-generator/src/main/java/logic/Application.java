package logic;

import IOoperations.InputParser;
import IOoperations.JsonWriter;
import IOoperations.OutputWriter;
import IOoperations.XMLWriter;
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

import model.Item;
import model.Transaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Application {
    public static final Logger logger = LogManager.getLogger(Application.class.getName());

    public static void main(String... args) {
        InputParser inputParser = InputParser.getInstance(args);
        TransactionGenerator transactionGenerator = new TransactionGenerator(inputParser);
        List<String[]> namePriceList = readInputFile(inputParser.getItemsFile());

        OutputWriter outputWriter = inputParser.getOutputWriter();
        String outDir = inputParser.getOutDir();
        createOutDir(outDir);

        long eventsCount = transactionGenerator.generateNumberOfEvents();
            outputWriter.saveToFile(eventsCount, outDir , transactionGenerator, namePriceList);
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

}
