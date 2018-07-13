package com.github.jasminb.k8sws.models;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * KeyValue model.
 *
 * @author jbegic
 */
public class KeyValue {
	private final String key;
	private final String value;

	/**
	 * Creates new KeyValue.
	 *
	 * @param key   {@link String} key
	 * @param value {@link String} value
	 */
	@JsonCreator
	public KeyValue(@JsonProperty("key") String key, @JsonProperty("value") String value) {
		this.key = key;
		this.value = value;
	}

	/**
	 * Gets key.
	 *
	 * @return the key
	 */
	public String getKey() {
		return key;
	}


	/**
	 * Gets value.
	 *
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
}
