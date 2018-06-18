package logic.IOoperations;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.stereotype.Service;

import javax.jms.*;

@Service
public class TopicProducer {

    public void produceTopic(String brokerUrl, String topicName, String messageString) throws JMSException {
        Connection connection = new ActiveMQConnectionFactory(brokerUrl).createConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic(topicName);
        MessageProducer messageProducer = session.createProducer(topic);
        messageProducer.send(session.createTextMessage(messageString));
        connection.close();
    }
}
