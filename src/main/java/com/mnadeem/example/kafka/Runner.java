package com.mnadeem.example.kafka;

import com.mnadeem.example.kafka.admin.Admin;
import com.mnadeem.example.kafka.consumer.DefaultConsumer;
import com.mnadeem.example.kafka.producer.SynchronousProducer;
import com.mnadeem.example.kafka.support.Arguments;

public class Runner {

	public static void main(String[] args) {

		Arguments arguments = Arguments.from(args);

		String brokers = arguments.getBrokers();
		String topicName = arguments.getTopicName();
		String groupId = arguments.getGroupId();

		switch (arguments.getOption()) {
		case PRODUCE:
			new SynchronousProducer().produce(brokers, topicName);
			break;
		case CONSUME:			
			new DefaultConsumer().consume(brokers, groupId, topicName);
			break;
		case DESCRIBE:
			Admin.describeTopics(brokers, topicName);
			break;
		case CREATE:
			Admin.createTopics(brokers, topicName);
			break;
		case DELETE:
			Admin.deleteTopics(brokers, topicName);
			break;
		case LIST:
			Admin.listTopics(brokers);
			break;
		default:
			arguments.printUsage();
		}
		System.exit(0);
	}
}
