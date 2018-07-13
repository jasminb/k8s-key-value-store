package com.github.jasminb.k8sws.services;

import com.github.jasminb.k8sws.models.KeyValue;

import java.util.Optional;

/**
 * Key/value service contract.
 *
 * @author jbegic
 */
public interface KeyValueService {

	Optional<KeyValue> get(String key);

	KeyValue set(KeyValue keyValue);

	void delete(String key);

	boolean isHealthy();
}
