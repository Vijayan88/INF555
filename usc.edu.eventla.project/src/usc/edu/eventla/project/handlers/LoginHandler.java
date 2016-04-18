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

public class LoginHandler extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Mongo mongoClient = new Mongo("localhost", 27017);
		DB database = mongoClient.getDB("eventsla");
		Cookie cookie = null;
		Cookie[] cookies = null;
		cookies = request.getCookies();
		String loginContents = "";
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				cookie = cookies[i];
				if (cookie.getName().equals("username")) {
					loginContents = loginDetails("loginuser.txt");
					loginContents = loginContents.replace("$USER", cookie.getValue());
				}
			}
		} else {
			loginContents = loginDetails("loginsimple.txt");
		}
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		InputStream in;
		in = getServletContext().getResourceAsStream("/index.html");
		BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
		StringBuilder sb = new StringBuilder();
		String line = null;
		while ((line = reader.readLine()) != null) {
			sb.append(line + "\n");
		}
		StringBuilder finalContent = new StringBuilder();
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
		
		out.println(sb.toString().replace("$LOGIN_DETAILS", loginContents).replace("$POPULAREVENTS", finalContent.toString()));
		// out.println(sb.toString().replace("$NO_EVENTS",events_cnt);

		return;
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
		out.println(sb.toString().replace("$NO_USERS", usrCount).replace("$REVIEW", String.valueOf(cursor.count())));
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
