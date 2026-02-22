package database;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class NoteDB {

    private static String getDatabaseName(String username) {
        return "user_" + username.replaceAll("[^a-zA-Z0-9]", "_");
    }

    public static List<String> getNoteTitles(String username) {
        List<String> titles = new ArrayList<>();
        String dbName = getDatabaseName(username);
        try {
            MongoDatabase database = DatabaseManager.getDatabase(dbName);
            if (database == null) return titles;
            MongoCollection<Document> collection = database.getCollection("notes");
            for (Document doc : collection.find()) {
                titles.add(doc.getString("title"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return titles;
    }

    public static Document getNote(String username, String title) {
        String dbName = getDatabaseName(username);
        try {
            MongoDatabase database = DatabaseManager.getDatabase(dbName);
            if (database == null) return null;
            MongoCollection<Document> collection = database.getCollection("notes");
            return collection.find(Filters.eq("title", title)).first();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void createNote(String username, String title, String content, String password) {
        String dbName = getDatabaseName(username);
        try {
            MongoDatabase database = DatabaseManager.getDatabase(dbName);
            if (database == null) return;
            MongoCollection<Document> collection = database.getCollection("notes");
            Document newNote = new Document("title", title)
                    .append("content", content)
                    .append("password", password);
            collection.insertOne(newNote);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteNote(String username, String title) {
        String dbName = getDatabaseName(username);
        try {
            MongoDatabase database = DatabaseManager.getDatabase(dbName);
            if (database == null) return;
            MongoCollection<Document> collection = database.getCollection("notes");
            collection.deleteOne(Filters.eq("title", title));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateNote(String username, String title, String newContent, String newPassword) {
        String dbName = getDatabaseName(username);
        try {
            MongoDatabase database = DatabaseManager.getDatabase(dbName);
            if (database == null) return;
            MongoCollection<Document> collection = database.getCollection("notes");
            Document update = new Document("$set", new Document("content", newContent).append("password", newPassword));
            collection.updateOne(Filters.eq("title", title), update);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void dropUserDatabase(String username) {
        String dbName = getDatabaseName(username);
        try {
            MongoDatabase database = DatabaseManager.getDatabase(dbName);
            if (database != null) {
                database.drop();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void renameUserDatabase(String oldUsername, String newUsername) {
        String oldDbName = getDatabaseName(oldUsername);
        String newDbName = getDatabaseName(newUsername);

        try {
            MongoDatabase adminDb = DatabaseManager.getDatabase("admin");

            Document command = new Document("copydb", 1)
                                .append("fromdb", oldDbName)
                                .append("todb", newDbName);

            adminDb.runCommand(command);

            MongoDatabase oldDatabase = DatabaseManager.getDatabase(oldDbName);
            if (oldDatabase != null) {
                oldDatabase.drop();
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to migrate user data. Please try again.", "Migration Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}