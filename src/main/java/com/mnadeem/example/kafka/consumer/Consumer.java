package com.mnadeem.example.kafka.consumer;

public interface Consumer {

	int consume(String brokers, String groupId, String topicName);
}
