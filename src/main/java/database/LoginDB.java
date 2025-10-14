package database;

import com.mongodb.client.*;
import org.bson.Document;

public class LoginDB {

    private static final String CONNECTION_STRING = "mongodb://localhost:27017";
    private static final String DATABASE_NAME = "LoginInfo";
    private static final String COLLECTION_NAME = "Logindetail";

    public static boolean authenticate(String email, String password) {
        try (MongoClient mongoClient = MongoClients.create(CONNECTION_STRING)) {
            MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
            MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);

            Document query = new Document("email", email).append("password", password);
            Document user = collection.find(query).first();

            return user != null; // returns true if a match is found
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
