package com.github.borisskert.aramalltimetable.store;


import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class Store {
    private static final String MONGO_ID_PROPERTY_NAME = "_id";

    private final MongoTemplate mongoTemplate;

    @Autowired
    public Store(
            MongoTemplate mongoTemplate
    ) {
        this.mongoTemplate = mongoTemplate;
    }

    public Map<String, Document> findAll(final String store) {
        MongoCollection<Document> collection = getCollection(store);
        return StreamSupport.stream(
                collection.find().spliterator(),
                false
        ).collect(
                Collectors.toMap(
                        (d) -> d.get(MONGO_ID_PROPERTY_NAME).toString(),
                        Function.identity()
                )
        );
    }

    public <T> Optional<T> find(final String store, final String id, Class<T> type) {
        Optional<Document> document = find(store, id);
        return document.map(d -> CloneUtils.deepClone(d, type, MONGO_ID_PROPERTY_NAME));
    }

    public <T> Optional<T> find(final String store, final Bson filter, Class<T> type) {
        Optional<Document> document = find(store, filter);
        return document.map(d -> CloneUtils.deepClone(d, type, MONGO_ID_PROPERTY_NAME));
    }

    public <T> void create(final String store, final String id, final T obj) {
        Document document = CloneUtils.deepClone(obj, Document.class, MONGO_ID_PROPERTY_NAME);
        create(store, id, document);
    }

    public <T> void update(final String store, final String id, final T obj) {
        Document document = CloneUtils.deepClone(obj, Document.class, MONGO_ID_PROPERTY_NAME);
        update(store, id, document);
    }

    public void update(final String store, final String id, final Document document) {
        MongoCollection<Document> collection = getCollection(store);
        UpdateResult updateResult = collection.replaceOne(idFilter(id), document);

        if (!updateResult.wasAcknowledged() || updateResult.getMatchedCount() < 1) {
            throw new RuntimeException();
        }
    }

    public void delete(final String store, final String id) {
        MongoCollection<Document> collection = getCollection(store);
        collection.deleteOne(idFilter(id));
    }

    private Optional<Document> find(final String store, final String id) {
        MongoCollection<Document> collection = getCollection(store);
        Document maybeFound = collection.find(idFilter(id)).first();
        return Optional.ofNullable(maybeFound);
    }

    private Optional<Document> find(final String store, final Bson filter) {
        MongoCollection<Document> collection = getCollection(store);
        Document maybeFound = collection.find(filter).first();
        return Optional.ofNullable(maybeFound);
    }

    private void create(final String store, final String id, final Document document) {
        MongoCollection<Document> collection = getCollection(store);

        collection.insertOne(
                document.append(MONGO_ID_PROPERTY_NAME, id)
        );
    }

    private MongoCollection<Document> getCollection(String store) {
        MongoCollection<Document> collection;

        if (mongoTemplate.collectionExists(store)) {
            collection = mongoTemplate.getCollection(store);
        } else {
            collection = mongoTemplate.createCollection(store);
        }
        return collection;
    }

    private Bson idFilter(String id) {
        return Filters.eq(id);
    }
}
