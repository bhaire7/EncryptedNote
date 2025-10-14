package database;

import com.mongodb.client.*;
import org.bson.Document;
import java.util.Arrays;
import java.util.List;

    public class MongodbConnection {
        public static void main(String[] args) {
            // Connect to MongoDB
            MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");

            // Select database and collection
            MongoDatabase database = mongoClient.getDatabase("LoginInfo");
            MongoCollection<Document> collection = database.getCollection("Logindetail");

            // Create multiple documents
            List<Document> Logindetail = Arrays.asList(
                    new Document("user_name", "baibhav").append("email", "Baibhavnepal07@gmail.com").append("password", "Password@07"),
                    new Document("user_name", "baibhav2").append("email", "Baibhavnepal05@gmail.com").append("password", "Password@05")
            );

            // Insert all documents at once
            collection.insertMany(Logindetail);

            System.out.println("✅ Multiple documents inserted successfully!");

            // Verify inserted data
            for (Document doc : collection.find()) {
                System.out.println(doc.toJson());
            }

            // Close client
            mongoClient.close();
        }
    }


