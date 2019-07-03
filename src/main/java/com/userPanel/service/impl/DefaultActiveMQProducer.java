package com.userPanel.service.impl;

import java.util.Map;
import java.util.ResourceBundle;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
//import com.userPanel.service.ActiveMQProducer;
//import org.springframework.stereotype.Service;

//@Service
public class DefaultActiveMQProducer {

	private ResourceBundle bundle;

	public void producerCall(String topicId,
			Map<String, String> property, String messageBody) {
		bundle = ResourceBundle.getBundle("serverConfig");
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
				"tcp://" + bundle.getString("activeMQ.serverIP") + ":"
						+ bundle.getString("activeMQ.serverPort"));
//		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
//				"tcp://" + "54.158.190.178" + ":"
//						+ "61616");
		Connection connection;
		try {
			connection = connectionFactory.createConnection();

			connection.start();
			Session session = connection.createSession(false,
					Session.AUTO_ACKNOWLEDGE);
			Topic topic = session.createTopic(topicId);
			MessageProducer producer = session.createProducer(topic);
			TextMessage message = session.createTextMessage();
			message.setText(messageBody);
			for (Object key : property.keySet()) {
				message.setStringProperty(key.toString().trim(),
						property.get(key).trim());
			}
			producer.setDeliveryMode(DeliveryMode.PERSISTENT);
			producer.send(message);
			connection.close();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
