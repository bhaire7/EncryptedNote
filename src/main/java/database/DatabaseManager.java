package database;

import javax.swing.JOptionPane;

import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class DatabaseManager {

    private static final String CONNECTION_STRING = "mongodb://localhost:27017";
    private static MongoClient mongoClient;

    // Gets the singleton MongoClient instance, creating it if it doesn't exist.
    public static MongoClient getMongoClient() {
        if (mongoClient == null) {
            try {
                mongoClient = MongoClients.create(CONNECTION_STRING);
                // A simple check to ensure the connection is alive.
                mongoClient.listDatabaseNames().first();
            } catch (MongoException e) {
                JOptionPane.showMessageDialog(null, "Failed to connect to MongoDB. Please ensure the server is running.", "Database Connection Error", JOptionPane.ERROR_MESSAGE);
                System.exit(1); // Terminate the application on connection failure
            }
        }
        return mongoClient;
    }

    public static MongoDatabase getDatabase(String dbName) {
        // Ensure we have a client, then get the database.
        return getMongoClient().getDatabase(dbName);
    }

    public static void close() {
        if (mongoClient != null) {
            mongoClient.close();
            mongoClient = null;
        }
    }
}