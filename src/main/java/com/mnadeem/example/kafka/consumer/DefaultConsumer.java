package com.mnadeem.example.kafka.consumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

public class DefaultConsumer implements Consumer {

	@Override
	public void consume(String brokers, String groupId, String topicName) {

		KafkaConsumer<String, String> consumer = buildConsumer(brokers, groupId, topicName);

		// Loop until ctrl + c
		int count = 0;
		while (true) {
			// Poll for records
			ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));
			// Did we get any?
			if (records.count() == 0) {
				// timeout/nothing to read
			} else {
				// Yes, loop over records
				for (ConsumerRecord<String, String> record : records) {
					// Display record and count
					count += 1;
					System.out.println(count + ": " + record.value());
				}
			}
		}
	}

	private KafkaConsumer<String, String> buildConsumer(String brokers, String groupId, String topicName) {
		KafkaConsumer<String, String> consumer = new KafkaConsumer<>(getProperties(brokers, groupId));

		// Subscribe to the 'test' topic
		consumer.subscribe(Arrays.asList(topicName));
		return consumer;
	}

	private Properties getProperties(String brokers, String groupId) {
		Properties properties = new Properties();
		properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers);
		properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

		// specify the protocol for Domain Joined clusters
		// properties.setProperty(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SASL_PLAINTEXT");
		return properties;
	}
}
