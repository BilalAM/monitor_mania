package com.monitor.utils;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 * @author saifasif
 */
public class MongoConnection {

    private static final MongoClient myClient = new MongoClient("localhost:27017");
    private static final MongoDatabase database = myClient.getDatabase("MonitorSet");
    private static final MongoCollection<Document> collection = database.getCollection("testCollection");

    public static MongoCollection<Document> getConnectionToColletion() {
        return collection;
    }

}
