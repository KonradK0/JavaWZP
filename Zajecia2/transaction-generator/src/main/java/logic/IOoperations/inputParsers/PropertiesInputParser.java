package logic.IOoperations.inputParsers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Properties;

@Service
public class PropertiesInputParser {

    private static final Logger logger = LogManager.getLogger(InputParser.class.getName());
    private Properties properties = new Properties();

    public PropertiesInputParser(){
        try {
            InputStream input = getClass().getResourceAsStream("/generator.properties");
            properties.load(input);
        } catch (java.io.IOException e) {
            logger.error("Unable to read or load properties file");
        }
    }

    public Properties getProperties() {
        return properties;
    }
}
