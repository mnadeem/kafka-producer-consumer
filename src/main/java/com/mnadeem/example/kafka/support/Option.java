package com.mnadeem.example.kafka.support;

public enum Option {
	PRODUCE("produce"), CONSUME("consume"), DESCRIBE("describe"), CREATE("create"), DELETE("delete"), LIST("list"),UNKNOWN("unknown");
	
	private String code;

	Option(String code) {
		this.code = code;
	}

	public static Option getOption(String value) {
		String trimmed = value.trim();
        for (Option option : values()) {
            if(option.code.equalsIgnoreCase(trimmed)) { return option; }
        }
        return UNKNOWN;
    }
	
	public boolean isUnknown() {
		return this == UNKNOWN;
	}
}
