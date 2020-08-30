package com.mnadeem.example.kafka.support;

import java.util.UUID;

public class Arguments {

	private String brokers;
	private String topicName;
	private Option option;
	private String groupId;

	private Arguments(String[] args) {

		if (args.length < 3) {
			printUsage();
		}

		this.brokers = args[0];
		this.option = Option.getOption(args[1]);
		this.topicName = args[2];

		if (args.length == 4) {
			this.groupId = args[3];
		} else {
			this.groupId = UUID.randomUUID().toString();
		}
	}

	public static Arguments from(String[] args) {
		return new Arguments(args);
	}

	public void printUsage() {
		System.out.println("Usage:");
		System.out.println("kafka-producer-consumer.jar <brokerhosts> <produce|consume|describe|create|delete> <topicName> [groupid]");
		System.exit(1);
	}

	public String getBrokers() {
		return brokers;
	}

	public String getTopicName() {
		return topicName;
	}

	public Option getOption() {
		return option;
	}

	public String getGroupId() {
		return groupId;
	}

	@Override
	public String toString() {
		return "Arguments [brokers=" + brokers + ", topicName=" + topicName + ", option=" + option + ", groupId="
				+ groupId + "]";
	}
}
