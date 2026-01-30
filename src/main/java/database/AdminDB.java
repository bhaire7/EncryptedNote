package database;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class AdminDB {
    private static final String DATABASE_NAME = "adminDb";
    private static final String COLLECTION_NAME = "admins";

    public static void initializeAdmin() {
        try {
            MongoDatabase database = DatabaseManager.getDatabase(DATABASE_NAME);
            if (database == null) return; // Connection failed
            MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);

            if (collection.countDocuments() == 0) {
                Document admin = new Document("admin_code", "123")
                        .append("username", "admin")
                        .append("password", "apassword");
                collection.insertOne(admin);
                System.out.println("Admin user created successfully.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean authenticate(String adminCode, String username, String password) {
        try {
            MongoDatabase database = DatabaseManager.getDatabase(DATABASE_NAME);
            if (database == null) return false; // Connection failed
            MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);

            Document query = new Document("admin_code", adminCode)
                    .append("username", username)
                    .append("password", password);
            return collection.find(query).first() != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}