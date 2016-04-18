package usc.edu.eventla.project.handlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
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
		String popup = request.getParameter("popup");
		String name = null;
		boolean valid = false;
		// to let users from cssl to test freely
		Mongo mongoClient = new Mongo("localhost", 27017);
		// String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
		DB database = mongoClient.getDB("eventsla");
		DBCollection collection = database.getCollection("Login");
		// DBCollection collection = database.getCollection("Event_Reg");
		// int events_cnt=collection.getCount();
		long users_cnt = collection.getCount();

		BasicDBObject allQuery = new BasicDBObject();
		allQuery.put("username", emailid);
		DBCursor cursor = collection.find(allQuery);
		if (cursor != null) {
			DBObject res = cursor.next();
			name = (String) res.get("name");
			String passwdChk = (String) res.get("passwd");
			if (passwd.equals(passwdChk)) {
				valid = true;
			}
		}

		Cookie cookie = new Cookie("username", name);
		cookie.setMaxAge(60 * 60 * 24);
		response.addCookie(cookie);

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		if (valid) {
			InputStream in;

			if ("admin@usc.edu".equals(emailid)) {

				String usrCount = Long.toString(users_cnt);
				listFeedbackDetails(out, usrCount, database);
				return;
			}

			else if (popup != null && popup != "") {
				in = getServletContext().getResourceAsStream("/Event.html");

			} else {
				in = getServletContext().getResourceAsStream("/ireg.html");
			}

			BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			StringBuilder sb = new StringBuilder();
			String line = null;

			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			String logContents = loginDetails("loginuser.txt");

			// find popular events
			StringBuilder finalContent = new StringBuilder();
			if (!(popup != null && popup != "")) {
			int max = 6;
			DB databaseEvents = mongoClient.getDB("eventsla");
			DBCollection collectionEvents = database.getCollection("CreateEvent");

			DBCursor cursorEve = collectionEvents.find();
			int count = 0;
			while (cursorEve.hasNext()) {
				if (count == 6)
					break;
				count++;
				DBObject res = cursorEve.next();
				in = getServletContext().getResourceAsStream("popularevent.txt");
				reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
				StringBuilder sb1 = new StringBuilder();
				line = null;
				while ((line = reader.readLine()) != null) {
					sb1.append(line + "\n");
				}
				String event_title = (String) res.get("event_title");
				BasicDBObject address = (BasicDBObject) res.get("address");
				String venue_place = "";
				if (address != null) {
					venue_place = (String) address.get("venue");
				}
				String imageName = "";
				imageName = (String) res.get("image_name");
                
				
				System.out.println(sb1.toString());
				finalContent.append(sb1.toString().replace("$event_title", event_title).
						 replace("$event_location", venue_place)
						.replace("$event_image", "/imagedownload?id=" + imageName));
			}
			}
			// DBCollection collection = database.getCollection("Event_Reg");

			out.println(sb.toString().replace("$LOGIN_DETAILS", logContents).replace("$USER", name).replace("$POPULAREVENTS", finalContent.toString()));
			// out.println(sb.toString().replace("$NO_EVENTS",events_cnt);

			return;
		}
	}

	public String loginDetails(String fileName) throws IOException {
		InputStream in;
		in = getServletContext().getResourceAsStream(fileName);
		BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
		StringBuilder sb = new StringBuilder();
		String line = null;
		while ((line = reader.readLine()) != null) {
			sb.append(line + "\n");
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		Mongo mongoClient = new Mongo("localhost", 27017);
		DB database = mongoClient.getDB("eventsla");
		DBCollection collection = database.getCollection("CONTACT");
		DBCursor cursor = collection.find();
		String name = null, email = "", phone = "", date = "", time = "", message = "";
		if (cursor != null) {
			while (cursor.hasNext()) {
				DBObject res = cursor.next();
				name = (String) res.get("name");
				email = (String) res.get("email");
				phone = (String) res.get("phone");
				date = (String) res.get("date");
				time = (String) res.get("time");
				message = (String) res.get("message");
				// in =
				// getServletContext().getResourceAsStream("admin_dash3.txt");
				// reader = new BufferedReader(new InputStreamReader(in,
				// "UTF-8"));
				// sb = new StringBuilder();
				// while ((line = reader.readLine()) != null) {
				// sb.append(line + "\n");
				// }
				System.out.println(name + email + phone);

			}
		}
		mongoClient.close();

	}

	public void listFeedbackDetails(PrintWriter out, String usrCount, DB database) throws IOException {

		String name = "", email = "", phone = "", date = "", time = "", message = "";

		//
		InputStream in;
		in = getServletContext().getResourceAsStream("adminheader.txt");
		BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
		StringBuilder sb = new StringBuilder();
		String line = null;
		while ((line = reader.readLine()) != null) {
			sb.append(line + "\n");
		}
		DBCollection collection = database.getCollection("contact");
		DBCursor cursor = collection.find();

		DBCollection collection1 = database.getCollection("CreateEvent");
		BasicDBObject allQuery = new BasicDBObject();
		allQuery.put("status", "approve");

		out.println(sb.toString().replace("$NO_EVENTS", String.valueOf(collection1.find(allQuery).count()))
				.replace("$NO_USERS", usrCount).replace("$REVIEW", String.valueOf(cursor.count())));
		if (cursor != null) {

			while (cursor.hasNext()) {

				DBObject res = cursor.next();
				name = (String) res.get("name");
				email = (String) res.get("email");
				phone = (String) res.get("phone");
				date = (String) res.get("published");
				message = (String) res.get("message");
				in = getServletContext().getResourceAsStream("admin_dash4.txt");
				reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
				sb = new StringBuilder();
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
				out.println(sb.toString().replace("$NAME", name).replace("$EMAIL", email).replace("$DATE", date)
						.replace("$MESSAGE", message));
				out.println("<hr>");

			}

			in = getServletContext().getResourceAsStream("adminfooter.txt");
			reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			sb = new StringBuilder();
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			out.println(sb.toString());
		}

	}

}
