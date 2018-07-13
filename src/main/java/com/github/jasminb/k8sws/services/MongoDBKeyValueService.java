package com.github.jasminb.k8sws.services;

import com.github.jasminb.k8sws.models.KeyValue;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;


/**
 * KeyValue service implementation based on MongoDB.
 *
 * @author jbegic
 */
@Component
public class MongoDBKeyValueService implements KeyValueService {
	private static final String KEY = UUID.randomUUID().toString();
	private static final String ID = "_id";
	private static final String VALUE = "v";

	private final MongoClient mongoClient;
	private final MongoCollection<BasicDBObject> collection;


	/**
	 * Creates new MongoDBKeyValueService.
	 *
	 * @param mongoService {@link String} service name/hostname/ip of the mongo server
	 * @param database {@link String} database name
	 * @param collectionName {@link String} collection name
	 */
	@Autowired
	public MongoDBKeyValueService(@Value("${mongo.service}") String mongoService,
								  @Value("${mongo.db}") String database,
								  @Value("${mongo.collection}") String collectionName) {

		this.mongoClient = new MongoClient(mongoService);
		this.collection = this.mongoClient.getDatabase(database).getCollection(collectionName, BasicDBObject.class);
	}

	public Optional<KeyValue> get(String key) {
		BasicDBObject entry = this.collection.find(new BasicDBObject(ID, key)).first();

		if (entry != null) {
			return Optional.of(new KeyValue(key, entry.getString(VALUE)));
		}

		return Optional.empty();
	}

	public KeyValue set(KeyValue keyValue) {
		BasicDBObject toSet = new BasicDBObject(VALUE, keyValue.getValue());
		this.collection.replaceOne(new BasicDBObject(ID, keyValue.getKey()), toSet, new UpdateOptions().upsert(true));
		return keyValue;
	}

	@Override
	public void delete(String key) {
		this.collection.deleteOne(new BasicDBObject(ID, key));
	}

	public boolean isHealthy() {
		return set(new KeyValue(KEY, KEY)) != null;
	}
}
