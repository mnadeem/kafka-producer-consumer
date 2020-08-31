package com.mnadeem.example.kafka.producer;

import java.util.Properties;
import java.util.Random;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

public class SynchronousProducer implements Producer {

	@Override
	public void produce(String brokers, String topicName) {
		Properties properties = getProperties(brokers);

		try (KafkaProducer<String, String> producer = new KafkaProducer<>(properties)) {
			// So we can generate random sentences
			Random random = new Random();
			String[] sentences = new String[] { "the cow jumped over the moon", "an apple a day keeps the doctor away",
					"four score and seven years ago", "snow white and the seven dwarfs", "i am at two with nature" };

			// Produce a bunch of records
			for (int i = 0; i < 100; i++) {
				// Pick a sentence at random
				String sentence = sentences[random.nextInt(sentences.length)];
				// Send the sentence to the test topic
				try {
					producer.send(new ProducerRecord<String, String>(topicName, sentence)).get();
					System.out.println(sentence + " : Sent!");
				} catch (Exception ex) {
					System.out.print(ex.getMessage());
				}
			}
		}
	}

	private Properties getProperties(String brokers) {
		Properties properties = new Properties();
		properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers);
		properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		// properties.setProperty(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SASL_PLAINTEXT");
		return properties;
	}
}
