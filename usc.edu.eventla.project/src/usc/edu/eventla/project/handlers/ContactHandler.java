package usc.edu.eventla.project.handlers;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;

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
		MongoCollection<Document> collection = database.getCollection("contact");
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		Document doc = new Document("name", name).append("email", email).append("phone", phone).append("message",
				message).append("published", dateFormat.format(date));
		collection.insertOne(doc);
		response.sendRedirect("index.html");
	}

}
