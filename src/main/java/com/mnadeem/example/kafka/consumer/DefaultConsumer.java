package com.mnadeem.example.kafka.consumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

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
		// Configure the consumer
		Properties properties = new Properties();
		// Point it to the brokers
		properties.setProperty("bootstrap.servers", brokers);
		// Set the consumer group (all consumers must belong to a group).
		properties.setProperty("group.id", groupId);
		// Set how to serialize key/value pairs
		properties.setProperty("key.deserializer", StringDeserializer.class.getName());
		properties.setProperty("value.deserializer", StringDeserializer.class.getName());
		// When a group is first created, it has no offset stored to start reading from.
		// This tells it to start
		// with the earliest record in the stream.
		properties.setProperty("auto.offset.reset", "earliest");

		// specify the protocol for Domain Joined clusters
		// properties.setProperty(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG,
		// "SASL_PLAINTEXT");
		return properties;
	}
}
