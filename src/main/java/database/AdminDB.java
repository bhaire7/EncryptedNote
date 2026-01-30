package database;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class AdminDB {
    private static final String URI = "mongodb://localhost:27017";
    private static final String DATABASE_NAME = "adminDb";
    private static final String COLLECTION_NAME = "admins";

    public static void initializeAdmin() {
        try (MongoClient mongoClient = MongoClients.create(URI)) {
            MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
            MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);

            if (collection.countDocuments() == 0) {
                Document admin = new Document("admin_code", "123")
                        .append("username", "admin")
                        .append("password", "apassword");
                collection.insertOne(admin);
                System.out.println("Admin user created successfully.");
            }
        }
    }

    public static boolean authenticate(String adminCode, String username, String password) {
        try (MongoClient mongoClient = MongoClients.create(URI)) {
            MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
            MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);

            Document query = new Document("admin_code", adminCode)
                    .append("username", username)
                    .append("password", password);
            return collection.find(query).first() != null;
        }
    }
}
