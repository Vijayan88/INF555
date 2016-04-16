package usc.edu.eventla.project.handlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

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
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;

public class EventListHandler extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String event_type = request.getParameter("event_type");
		String city_name = request.getParameter("city_name");
		String venue = request.getParameter("venue");
		String start_date = request.getParameter("start_date");

		String event_title = null;
		String event_description = null;
		String venue_place = null;
		String address1 = null;
		String starting_time = null;
		String type_event = null;
		String region = null;

		// to let users from cssl to test freely
		Mongo mongoClient = new Mongo("localhost", 27017);
		// String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
		DB database = mongoClient.getDB("eventsla");
		DBCollection collection = database.getCollection("CreateEvent");
		// DBCollection collection = database.getCollection("Event_Reg");
		// int events_cnt=collection.getCount();
		// long users_cnt = collection.getCount();

		BasicDBObject allQuery = new BasicDBObject();
		List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
		obj.add(new BasicDBObject("event_type", event_type));
		obj.add(new BasicDBObject("city_name", city_name));
		obj.add(new BasicDBObject("start_date", start_date));
		obj.add(new BasicDBObject("address.venue", venue));

		allQuery.put("$or", obj);

		

		PrintWriter out = response.getWriter();
		InputStream in;
		in = getServletContext().getResourceAsStream("eventlisttemplate1.txt");
		BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
		StringBuilder sb = new StringBuilder();
		String line = null;
		while ((line = reader.readLine()) != null) {
			sb.append(line + "\n");
		}

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
		out.println(sb.toString().replace("$LOGIN_DETAILS", loginContents).replace("$event_type", event_type)
				.replace("$city_name", city_name));

		// mongodb
		DBCursor cursor = collection.find(allQuery);

		if (cursor == null) {
			cursor = collection.find();
		}
		while (cursor.hasNext()) {

			DBObject res = cursor.next();
			event_title = (String) res.get("event_title");
			type_event = (String) res.get("event_type");
			BasicDBObject address = (BasicDBObject) res.get("address");
			venue_place = (String) address.get("venue");
			address1 = (String) address.get("address1");
			starting_time = (String) address.get("start_time");
			region = (String) address.get("city_name");
			String imageName = (String) res.get("image_name");
			GridFS gfsPhoto = new GridFS(database, "photo");
			GridFSDBFile imageForOutput = gfsPhoto.findOne(imageName);

			// save it into a new image file
			imageForOutput.writeTo("C:\\Users\\ayush\\Desktop\\test.jpg"); 
			// event_address=
			// event_category = (String)res.get("event_category");

			// BasicDBList res = (BasicDBList) res.next().get("address");
			event_description = (String) res.get("event_description");
			in = getServletContext().getResourceAsStream("eventlisttemplate2.txt");
			reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			sb = new StringBuilder();
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			out.println(sb.toString().replace("$event_title", event_title).replace("$venue", venue_place)
					.replace("$address1", address1).replace("$start_time", starting_time)
					.replace("$event_description", event_description).replace("$event_image", "C:\\Users\\ayush\\Desktop\\test.jpg"));
			
		}
			

			
			
		in = getServletContext().getResourceAsStream("/eventlisttemplate3.txt");
		reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
		sb = new StringBuilder();
		while ((line = reader.readLine()) != null) {
			sb.append(line + "\n");
		}
		out.println(sb.toString());

	}

	public static void main(String[] args) {
		// to let users from cssl to test freely
		Mongo mongoClient = new Mongo("localhost", 27017);
		// String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
		DB database = mongoClient.getDB("eventsla");
		DBCollection collection = database.getCollection("CreateEvent");
		// DBCollection collection = database.getCollection("Event_Reg");
		// int events_cnt=collection.getCount();
		// long users_cnt = collection.getCount();
		String event_title = null;
		String event_description = null;
		String venue_place = null;
		String address1 = null;
		String starting_time = null;
		String type_event = null;
		String region = null;
		BasicDBObject allQuery = new BasicDBObject();
		List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
		obj.add(new BasicDBObject("event_type", "MT"));
		obj.add(new BasicDBObject("city_name", "TV"));
		obj.add(new BasicDBObject("start_date", "2016-04-15"));
		obj.add(new BasicDBObject("address.venue", "SC"));

		allQuery.put("$or", obj);

		DBCursor cursor = collection.find(allQuery);
		if (cursor != null) {
			while (cursor.hasNext()) {
				DBObject res = cursor.next();
				event_title = (String) res.get("event_title");
				type_event = (String) res.get("event_type");
				BasicDBObject address = (BasicDBObject) res.get("address");
				venue_place = (String) address.get("venue");
				address1 = (String) address.get("address1");
				starting_time = (String) address.get("start_time");
				region = (String) address.get("city_name");
				// event_address=
				// event_category = (String)res.get("event_category");

				// BasicDBList res = (BasicDBList) res.next().get("address");
				event_description = (String) res.get("event_description");

				System.out.println(event_title);
				System.out.println(type_event);
				System.out.println(venue_place);
				System.out.println(address1);
				System.out.println(starting_time);
				System.out.println(region);

			}
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
}