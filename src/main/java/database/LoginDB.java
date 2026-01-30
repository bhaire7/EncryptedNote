package database;

import org.bson.Document;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class LoginDB {

    private static final String DATABASE_NAME = "LoginInfo";
    private static final String COLLECTION_NAME = "Logindetail";

    public static String authenticate(String email, String password) {
        try {
            MongoDatabase database = DatabaseManager.getDatabase(DATABASE_NAME);
            if (database == null) return null; // Connection failed
            MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);

            Document query = new Document("email", email).append("password", password);
            Document user = collection.find(query).first();

            if (user != null) {
                return user.getString("user_name");
            }
            return null; // Return null if authentication fails
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}