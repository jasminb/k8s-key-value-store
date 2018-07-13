package com.github.jasminb.k8sws.apis;

import com.github.jasminb.k8sws.exceptions.KeyNotFoundException;
import com.github.jasminb.k8sws.exceptions.ServiceUnhealthyException;
import com.github.jasminb.k8sws.models.KeyValue;
import com.github.jasminb.k8sws.services.MongoDBKeyValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Implements simple Key/Value API that allows for creating and updating key mappings.
 *
 * @author jbegic
 */
@RestController
@RequestMapping(value = "/api/v1", produces = "application/json")
public class KeyValueAPI {

	private final MongoDBKeyValueService keyValueService;

	/**
	 * Creates new KeyValueAPI.
	 *
	 * @param keyValueService {@link MongoDBKeyValueService} service instance
	 */
	@Autowired
	public KeyValueAPI(MongoDBKeyValueService keyValueService) {
		this.keyValueService = keyValueService;
	}


	/**
	 * Returns predefined key/value pair in case service is operational.
	 *
	 * @return {@link KeyValue}
	 * @throws ServiceUnhealthyException thrown in case communication with MongoDB fails
	 */
	@RequestMapping(value = "/health", method = RequestMethod.GET)
	public KeyValue get() throws ServiceUnhealthyException {
		if (keyValueService.isHealthy()) {
			return new KeyValue("healthy", "true");
		}
		throw new ServiceUnhealthyException();
	}

	/**
	 * Retrieves existing key/value mapping if it exists.
	 *
	 * @return {@link KeyValue}
	 * @throws KeyNotFoundException thrown in case no mapping exists for a given key
	 */
	@RequestMapping(value = "/keys/{key}", method = RequestMethod.GET)
	public KeyValue get(@PathVariable("key") String key) throws KeyNotFoundException {
		return keyValueService.get(key).orElseThrow(KeyNotFoundException::new);
	}

	/**
	 * Creates new or updates existing key/value mapping.
	 *
	 * @param keyValue {@link KeyValue} mapping to create
	 * @return {@link KeyValue}
	 */
	@RequestMapping(value = "/keys", method = RequestMethod.POST)
	public KeyValue set(@RequestBody KeyValue keyValue) {
		return keyValueService.set(keyValue);
	}

	/**
	 * Deletes existing key/value mapping if it exists.
	 */
	@RequestMapping(value = "/keys/{key}", method = RequestMethod.DELETE)
	public void delete(@PathVariable("key") String key) {
		keyValueService.delete(key);
	}
}
