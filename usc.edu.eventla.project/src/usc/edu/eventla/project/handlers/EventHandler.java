package usc.edu.eventla.project.handlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class EventHandler extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String emailid = request.getParameter("emailid");
		String passwd = request.getParameter("passwd");
		// to let users from cssl to test freely
		Mongo mongoClient = new Mongo("localhost", 27017);
		String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
		DB database = mongoClient.getDB("eventsla");
		DBCollection collection = database.getCollection("Login");

		BasicDBObject allQuery = new BasicDBObject();
		allQuery.put("username", emailid);
		DBCursor cursor = collection.find(allQuery);
		DBObject res = cursor.next();
		String name=(String) res.get("name");
		mongoClient.close();

		response.setContentType("text/html");

		// Actual logic goes here.
		PrintWriter out = response.getWriter();

		InputStream in = getServletContext().getResourceAsStream("/ireg.html");

		BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
		StringBuilder sb = new StringBuilder();
		String line = null;

		while ((line = reader.readLine()) != null) {
			sb.append(line + "\n");
		}
		out.println(sb.toString().replace("$USER", name));

	}

	public static void main(String[] args) {
		Mongo mongoClient = new Mongo("localhost", 27017);
		String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
		DB database = mongoClient.getDB("eventsla");
		DBCollection collection = database.getCollection("Login");

		BasicDBObject allQuery = new BasicDBObject();
		allQuery.put("username", "ayushi@usc.edu");
		String name="";
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
