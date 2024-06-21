package com.demo.database;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoDb
{

    private MongoDatabase mongoDatabase;
    private MongoCollection<Document> mongoCollection;

    public MongoDb()
    {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        mongoDatabase = mongoClient.getDatabase("shopping");
        mongoCollection = mongoDatabase.getCollection("products");
    }

    public MongoDatabase getMongoDatabase() {
        return mongoDatabase;
    }

    public void setMongoDatabase(MongoDatabase mongoDatabase) {
        this.mongoDatabase = mongoDatabase;
    }

    public MongoCollection<Document> getMongoCollection() {
        return mongoCollection;
    }

    public void setMongoCollection(MongoCollection<Document> mongoCollection) {
        this.mongoCollection = mongoCollection;
    }

}
