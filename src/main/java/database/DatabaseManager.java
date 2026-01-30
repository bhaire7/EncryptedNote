package database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.MongoException;
import javax.swing.JOptionPane;

public class DatabaseManager {

    private static final String CONNECTION_STRING = "mongodb://localhost:27017";
    private static MongoClient mongoClient;

    public static MongoDatabase getDatabase(String dbName) {
        if (mongoClient == null) {
            try {
                mongoClient = MongoClients.create(CONNECTION_STRING);
                // The is-a relationship check with throw exception if connection fails.
                mongoClient.listDatabaseNames().first();
            } catch (MongoException e) {
                JOptionPane.showMessageDialog(null, "Failed to connect to MongoDB. Please ensure the server is running.", "Database Connection Error", JOptionPane.ERROR_MESSAGE);
                System.exit(1); // Terminate the application
                return null;
            }
        }
        return mongoClient.getDatabase(dbName);
    }

    public static void close() {
        if (mongoClient != null) {
            mongoClient.close();
            mongoClient = null;
        }
    }
}
