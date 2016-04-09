package usc.edu.eventla.project.handlers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class ContactHandler extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String message = request.getParameter("message");
		MongoClient mongoClient = new MongoClient();

		MongoDatabase database = mongoClient.getDatabase("eventsla");
		MongoCollection<Document> collection = database.getCollection("CONTACT");
		
		Document doc = new Document("name", name).append("email", email).append("phone", phone)
				.append("message", message);
		collection.insertOne(doc);
		response.sendRedirect("index.html");
	}

	public static void main(String[] args) {
		Mongo mongoClient = new Mongo("localhost", 27017);
		String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
		DB database = mongoClient.getDB("eventsla");
		DBCollection collection = database.getCollection("Login");

		BasicDBObject allQuery = new BasicDBObject();
		allQuery.put("username", "ayushi@usc.edu");
		String name = "";
		DBCursor cursor = collection.find(allQuery);
		while (cursor.hasNext()) {
			DBObject res = cursor.next();
			if (res.get("username").equals("ayushi@usc.edu")) {
				name = (String) res.get("name");
			}
		}
		System.out.println(name);
		mongoClient.close();

	}

}
