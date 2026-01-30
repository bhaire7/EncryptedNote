package database;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class NoteDB {

    private static final String CONNECTION_STRING = "mongodb://localhost:27017";

    // A helper method to get a clean database name from an email
    private static String getDatabaseName(String email) {
        return "user_" + email.replaceAll("[^a-zA-Z0-9]", "_");
    }

    // Fetches all note titles for a specific user
    public static List<String> getNoteTitles(String email) {
        List<String> titles = new ArrayList<>();
        String dbName = getDatabaseName(email);
        try {
            MongoDatabase database = DatabaseManager.getDatabase(dbName);
            if (database == null) return titles; // Connection failed
            MongoCollection<Document> collection = database.getCollection("notes");
            for (Document doc : collection.find()) {
                titles.add(doc.getString("title"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return titles;
    }

    // Fetches a single note's content and password
    public static Document getNote(String email, String title) {
        String dbName = getDatabaseName(email);
        try {
            MongoDatabase database = DatabaseManager.getDatabase(dbName);
            if (database == null) return null; // Connection failed
            MongoCollection<Document> collection = database.getCollection("notes");
            return collection.find(Filters.eq("title", title)).first();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Creates a new note
    public static void createNote(String email, String title, String content, String password) {
        String dbName = getDatabaseName(email);
        try {
            MongoDatabase database = DatabaseManager.getDatabase(dbName);
            if (database == null) return; // Connection failed
            MongoCollection<Document> collection = database.getCollection("notes");
            Document newNote = new Document("title", title)
                    .append("content", content) // Should be encrypted
                    .append("password", password); // Hashed password for decryption
            collection.insertOne(newNote);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Deletes a note
    public static void deleteNote(String email, String title) {
        String dbName = getDatabaseName(email);
        try {
            MongoDatabase database = DatabaseManager.getDatabase(dbName);
            if (database == null) return; // Connection failed
            MongoCollection<Document> collection = database.getCollection("notes");
            collection.deleteOne(Filters.eq("title", title));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Updates a note's content and password
    public static void updateNote(String email, String title, String newContent, String newPassword) {
        String dbName = getDatabaseName(email);
        try {
            MongoDatabase database = DatabaseManager.getDatabase(dbName);
            if (database == null) return; // Connection failed
            MongoCollection<Document> collection = database.getCollection("notes");
            Document update = new Document("$set", new Document("content", newContent).append("password", newPassword));
            collection.updateOne(Filters.eq("title", title), update);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}