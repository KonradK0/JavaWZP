package logic.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.util.DefaultXmlPrettyPrinter;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import logic.IOoperations.QueueProducer;
import logic.IOoperations.TopicProducer;
import logic.IOoperations.inputParsers.InputParser;
import model.Transaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;

@Service
public class ActiveMQUtils {
    private static final Logger logger = LogManager.getLogger(ActiveMQUtils.class.getName());

    private InputParser inputParser = new InputParser(new ApplicationWrapper(), new RandomGenerator());

    public void sendTransaction(Transaction transaction) {
        if (inputParser.validateBrokerProperties()) {
            String brokerUrl = inputParser.getBrokerUrl();
            String queueName = inputParser.getQueueName();
            String topicName = inputParser.getTopicName();
            if (inputParser.validateQueueName(queueName)) {
                QueueProducer queueProducer = new QueueProducer();
                try {
                    queueProducer.produceQueue(brokerUrl, queueName, formatTransactionMessage(inputParser.getOutputFormat(), transaction));
                } catch (JMSException e) {
                    logger.error(e.getMessage());
                }
            }
            if(inputParser.validateTopicName(topicName)){
                TopicProducer topicProducer = new TopicProducer();
                try {
                    topicProducer.produceTopic(brokerUrl, topicName, formatTransactionMessage(inputParser.getOutputFormat(), transaction));
                } catch (JMSException e) {
                    logger.error(e.getMessage());
                }
            }
        }
    }

    public String formatTransactionMessage(String format, Transaction transaction) {
        try {
            switch (format) {
                case ("xml"):
                    XmlMapper xmlMapper = new XmlMapper();
                    xmlMapper.setDefaultPrettyPrinter(new DefaultXmlPrettyPrinter());
                    return xmlMapper.writeValueAsString(transaction);
                case ("yaml"):
                    YAMLMapper yamlMapper = new YAMLMapper();
                    yamlMapper.setDefaultPrettyPrinter(new DefaultPrettyPrinter());
                    return yamlMapper.writeValueAsString(transaction);
                default:
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    return gson.toJson(transaction);
            }
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
            return "";
        }
    }

}
