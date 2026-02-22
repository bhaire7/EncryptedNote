package database;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

public class UserDB {

    private static final String CONNECTION_STRING = "mongodb://localhost:27017";
    private static final String DB_NAME = "LoginInfo";
    private static final String COLLECTION_NAME = "Logindetail";

    // Fetches all users from the database
    public static List<Document> getAllUsers() {
        List<Document> users = new ArrayList<>();
        try (MongoClient mongoClient = MongoClients.create(CONNECTION_STRING)) {
            MongoDatabase database = mongoClient.getDatabase(DB_NAME);
            MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);
            // Exclude the admin user from the list
            for (Document doc : collection.find(Filters.ne("user_name", "admin"))) {
                users.add(doc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    // Updates a user's details
    public static void updateUser(String originalEmail, String newUsername, String newEmail, String newPassword) {
        try (MongoClient mongoClient = MongoClients.create(CONNECTION_STRING)) {
            MongoDatabase database = mongoClient.getDatabase(DB_NAME);
            MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);

            Bson filter = Filters.eq("email", originalEmail);
            List<Bson> updates = new ArrayList<>();
            updates.add(Updates.set("user_name", newUsername));
            updates.add(Updates.set("email", newEmail));
            if (newPassword != null && !newPassword.isEmpty()) {
                updates.add(Updates.set("password", newPassword));
            }
            Bson update = Updates.combine(updates);

            collection.updateOne(filter, update);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Deletes a user
    public static void deleteUser(String email) {
        try (MongoClient mongoClient = MongoClients.create(CONNECTION_STRING)) {
            MongoDatabase database = mongoClient.getDatabase(DB_NAME);
            MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);
            collection.deleteOne(Filters.eq("email", email));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}