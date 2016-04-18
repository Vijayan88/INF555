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

public class EventDescHandler extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.sendRedirect("/home");
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String event_title = request.getParameter("id");
		String title_event = null;
		String event_description = null;

		String venue = null;
		String address1 = null;
		String address2 = null;
		String cityname = null;
		String pin_code = null;
		String start_date = null;
		String start_time = null;
		String event_website = null;
		String ticket_price1 = null;
		String ticket_price2 = null;
		String ticket_price3 = null;
		String ticket_name1 = null;
		String ticket_name2 = null;
		String facebook_link = null;
		String twitter_link = null;

		// to let users from cssl to test freely
		Mongo mongoClient = new Mongo("localhost", 27017);

		DB database = mongoClient.getDB("eventsla");
		DBCollection collection = database.getCollection("CreateEvent");

		BasicDBObject allQuery = new BasicDBObject();

		allQuery.put("event_title", event_title);

		PrintWriter out = response.getWriter();
		InputStream in;
		in = getServletContext().getResourceAsStream("eventdesctemplate1.txt");
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

		loginContents = sb.toString().replace("$LOGIN_DETAILS", loginContents);

		out.println(loginContents);

		// mongodb
		DBCursor cursor = collection.find(allQuery);

		if (cursor != null) {
			DBObject res = cursor.next();

			title_event = (String) res.get("event_title");
			event_description = (String) res.get("event_description");
			BasicDBObject address = (BasicDBObject) res.get("address");
			venue = (String) address.get("venue");
			address1 = (String) address.get("address1");
			address2 = (String) address.get("address2");
			cityname = (String) address.get("city_name");
			pin_code = (String) address.get("pin_code");
			start_date = (String) address.get("start_date");
			start_time = (String) address.get("start_time");
			event_website = (String) res.get("event_website");
			facebook_link = (String) res.get("facebook_link");
			twitter_link = (String) res.get("twitter_link");
			BasicDBObject ticket_1 = (BasicDBObject) res.get("ticket_1");
			ticket_name1 = (String) ticket_1.get("ticket_name1");
			ticket_price1 = (String) ticket_1.get("ticket_price1");
			BasicDBObject ticket_2 = (BasicDBObject) res.get("ticket_2");
			ticket_name2 = (String) ticket_2.get("ticket_name2");
			ticket_price2 = (String) ticket_2.get("ticket_price2");
			String imageName = (String) res.get("image_name");
			String maps = (String) address.get("maps");
			String coordinates ="";
			if (maps != null && maps.length() > 0) {
				String coords[] = maps.split("@");
				if(coords.length > 1){
					coordinates = coords[1];
				}
			}
			in = getServletContext().getResourceAsStream("eventdesctemplate2.txt");
			reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			sb = new StringBuilder();
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			out.println(sb.toString().replace("$event_title", title_event)
					.replace("$event_description", event_description).replace("$venue", venue)
					.replace("$address1", address1).replace("$address2", address2).replace("$cityname", cityname)
					.replace(" $pin_code", pin_code).replace("$start_date", start_date)
					.replace("$start_time", start_time).replace("$city_name", cityname)
					.replace("$ticket_price1", ticket_price1).replace("$ticket_price2", ticket_price2)
					.replace("$event_website", event_website).replace("$ticket_name1", ticket_name1)
					.replace("$ticket_name2", ticket_name2).replace("$ticket_price2", ticket_price2)
					.replace("$fb_link", facebook_link).replace("$twiiter_link", twitter_link)
					.replace("$event_image", "/imagedownload?id=" + imageName).replace("$COORDS", coordinates)

			);

			out.println(sb.toString().replace("$event_title", title_event).replace("$event_description", event_description).
					                         replace("$venue",venue).replace("$address1", address1).replace("$address2",address2)
					                         .replace("$cityname",cityname).replace(" $pin_code",pin_code)
					                         .replace("$start_date" , start_date).replace("$start_time",start_time)
					                         .replace("$city_name", cityname).replace("$ticket_price1", ticket_price1)
					                        . replace("$ticket_price2",ticket_price2)
					                        .replace("$start_date", start_date)
					                        .replace("$event_website", event_website)
					                         .replace("$ticket_name1", ticket_name1)
					                         .replace("$ticket_name2", ticket_name2)
					                         .replace("$ticket_price2", ticket_price2)
					                         .replace("$fb_link", facebook_link)
					                         .replace("$twiiter_link", twitter_link)
					                         .replace("$event_image", "/imagedownload?id="+imageName)
					                         
					                         );
		
		}
			

		in = getServletContext().getResourceAsStream("/eventdesctemplate3.txt");
		reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
		sb = new StringBuilder();
		while ((line = reader.readLine()) != null) {
			sb.append(line + "\n");
		}
		out.println(sb.toString());

		StringBuilder finalContent = new StringBuilder();
		int max = 6;
		DB databaseEvents = mongoClient.getDB("eventsla");
		DBCollection collectionEvents = database.getCollection("CreateEvent");

		DBCursor cursorEve = collectionEvents.find();
		int count = 0;
		while (cursorEve.hasNext()) {
			if (count == 4)
				break;
			count++;
			DBObject res = cursorEve.next();
			in = getServletContext().getResourceAsStream("recomendation.txt");
			reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			StringBuilder sb1 = new StringBuilder();
			line = null;
			while ((line = reader.readLine()) != null) {
				sb1.append(line + "\n");
			}
			event_title = (String) res.get("event_title");

			String imageName = "";
			imageName = (String) res.get("image_name");

			System.out.println(sb1.toString());
			finalContent.append(sb1.toString().replace("$event_image", "/imagedownload?id=" + imageName)
					.replace("$event_title", event_title));
		}

		// out.println(sb.toString().replace("$NO_EVENTS",events_cnt);

		out.println(sb.toString().replace("$POPULAREVENTS", finalContent.toString()));

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
		String starting_date = null;
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
				starting_date=(String) address.get("start_date");
				region = (String) address.get("city_name");
				// event_address=
				// event_category = (String)res.get("event_category");

				// BasicDBList res = (BasicDBList) res.next().get("address");
				event_description = (String) res.get("event_description");

				

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