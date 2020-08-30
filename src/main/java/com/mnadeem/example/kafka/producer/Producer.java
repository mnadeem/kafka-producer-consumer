package com.mnadeem.example.kafka.producer;

public interface Producer {

	void produce(String brokers, String topicName);
}
