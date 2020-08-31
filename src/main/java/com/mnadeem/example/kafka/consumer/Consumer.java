package com.mnadeem.example.kafka.consumer;

public interface Consumer {

	void consume(String brokers, String groupId, String topicName);
}
