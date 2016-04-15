package usc.edu.eventla.project.handlers;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class LogIn {
public static void main(String[] args) {
	MongoClient mongoClient = new MongoClient();

	MongoDatabase database = mongoClient.getDatabase("eventsla");
	MongoCollection<Document> collection = database.getCollection("Login");
	Document doc = new Document("name", "ayushi").append("username", "ayushi@usc.edu").append("passwd", "1234");
	collection.insertOne(doc);
	mongoClient.close();
}
}
