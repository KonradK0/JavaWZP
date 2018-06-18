package logic.IOoperations;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.jms.*;

@Service
public class QueueProducer {
    private static final Logger logger = LogManager.getLogger(QueueProducer.class.getName());
    public void produceQueue(String brokerUrl, String queueName, String messageString) throws JMSException {
        Connection connection = new ActiveMQConnectionFactory(brokerUrl).createConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(queueName);
        MessageProducer messageProducer = session.createProducer(queue);
        messageProducer.send(session.createTextMessage(messageString));
        connection.close();
    }
}
