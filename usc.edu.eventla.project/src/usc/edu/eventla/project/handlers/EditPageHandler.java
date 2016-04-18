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

public class EditPageHandler extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		 String event_title = request.getParameter("id");
		 String title_event = null;
			String etype = null;
			String online = null;
			String location = null;
			String venue = null;
			String addr = null;
			String addr2 = null;
			String city = null;
			String pincode=null;
			String state = null;
			String maps = null;
			String start_date=null;
			String start_time = null;
			String end_date=null;
			String end_time = null;
			String time_zone = null;
			String thumbnil = null;
			String edescription = null;
			String event_website=null;
			String organizer_name = "";
			String organizer_desc = "";
			String facebook = null;
			String twitter = null;
			String ticket_type1 = null;
			String ticket_name1 = null;
			String ticket_qty1 = null;
			String ticket_price1 = null;
			String ticket_type2 = null;
			String ticket_name2 = null;
			String ticket_qty2 = null;
			String ticket_price2 = null;
			String ticket_type3 = null;
			String ticket_name3 = null;
			String ticket_qty3 = null;
			String ticket_price3 = null;
			String public_page = null;
			String private_page = null;
			
		// to let users from cssl to test freely
		Mongo mongoClient = new Mongo("localhost", 27017);
		
		DB database = mongoClient.getDB("eventsla");
		DBCollection collection = database.getCollection("CreateEvent");
		

		BasicDBObject allQuery = new BasicDBObject();
		
		allQuery.put("event_title", event_title);
	
		PrintWriter out = response.getWriter();
		InputStream in;
		in = getServletContext().getResourceAsStream("EditEvents.html");
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
		
		
		
		
		//out.println(loginContents);
		
		// mongodb
		DBCursor cursor = collection.find(allQuery);

		
		if (cursor != null) {
			DBObject res = cursor.next();
			
			title_event = (String) res.get("event_title");
			etype = (String) res.get("event_type");
			online = (String) res.get("online");
			location = (String) res.get("location");
			BasicDBObject address = (BasicDBObject) res.get("address");
			venue = (String) address.get("venue");
			addr = (String) address.get("address1");
			addr2 = (String) address.get("address2");
			city = (String) address.get("city_name");
			pincode = (String) address.get("pin_code");
			state = (String) address.get("state");
			maps = (String) address.get("maps");
			start_date = (String) address.get("start_date");
			start_time = (String) address.get("start_time");
			end_date = (String) address.get("start_date");
			end_time = (String) address.get("start_time");
			time_zone = (String) address.get("time_zone");
			event_website=(String) res.get("event_website");
			edescription = (String) res.get("event_description");
			organizer_name = (String) res.get("organiser_name");
			organizer_desc = (String) res.get("organizer_desc");
			facebook=(String) res.get("facebook_link");
			twitter=(String) res.get("twitter_link");
			BasicDBObject ticket_1  = (BasicDBObject) res.get("ticket_1");
			ticket_type1 = (String) ticket_1.get("ticket_type1");
			ticket_name1 = (String) ticket_1.get("ticket_name1");
			ticket_qty1 = (String) ticket_1.get("quantity_present1");
			ticket_price1 = (String)ticket_1.get("ticket_price1");
			BasicDBObject ticket_2  = (BasicDBObject) res.get("ticket_2");
			ticket_type2 = (String) ticket_1.get("ticket_type2");
			ticket_name2 = (String) ticket_1.get("ticket_name2");
			ticket_qty2 = (String) ticket_1.get("quantity_present2");
			ticket_price2 = (String)ticket_1.get("ticket_price2");
			BasicDBObject ticket_3  = (BasicDBObject) res.get("ticket_3");
			ticket_type3 = (String) ticket_1.get("ticket_type3");
			ticket_name3 = (String) ticket_1.get("ticket_name3");
			ticket_qty3 = (String) ticket_1.get("quantity_present3");
			ticket_price3 = (String)ticket_1.get("ticket_price3");			
			public_page = (String) res.get("public");
			private_page = (String) res.get("private");
			thumbnil = (String) res.get("image_name");
		 if(online == null)
			 online = "";
		 if(etype == null)
			 etype = "";
		 if(public_page == null)
			 public_page ="";
		 if(private_page == null)
			 private_page ="";
		if (location == null )
			location="";
		if (venue == null)
			venue ="";
		if (addr == null)
			addr="";
		if (addr2 ==null)
			addr2="";
		if (city == null)
			city ="";
		if (state == null)
			state="";
		if (pincode==null)
			pincode="";
		if(maps==null)
			maps="";
		if (start_date==null)
			start_date="";
		if(start_time ==null)
			start_time="";
		if(end_time==null)
			end_time="";
		if(end_date==null)
			end_date="";
		if(time_zone==null)
			time_zone="";
		if(edescription==null)
			edescription="";
		if(event_website==null)
			event_website="";
		if(organizer_name==null)
			organizer_name="";
		if(organizer_desc==null)
			organizer_desc="";
		if(facebook==null)
			facebook="";
		if(twitter==null)
			twitter="";
		if(ticket_type1==null)
			ticket_type1="";
		if(ticket_type2==null)
			ticket_type2="";
		if(ticket_type3==null)
			ticket_type3="";
		if(ticket_name1==null)
          ticket_name1="";
		if(ticket_name2==null)
			ticket_name2="";
		if(ticket_name3==null)
			ticket_name3="";
		if(ticket_qty1==null)
			ticket_qty1="";
		if(ticket_qty2==null)
			ticket_qty2="";
		if(ticket_qty3==null)
			ticket_qty3="";
		if(ticket_price1==null)
			ticket_price1="";
		if(ticket_price2==null)
			ticket_price2="";
		if(ticket_price3==null)
			ticket_price3="";
		if(public_page==null)
			public_page="";
		if(private_page==null)
			private_page="";
		
		 String str = loginContents.replace("$EVENT_TITLE",title_event)
                 .replace("$EVENT_TYPE", etype)
                 .replace("$ONLINE",online)
                .replace("$LOCATION", location)
                .replace("$VENUE",venue)
                 .replace("$ADDRESS1",addr)
                 .replace("$ADDRESS2",addr2)
                 .replace("$CITY",city)
                 .replace("$STATE",state)
                 .replace("$PINCODE",pincode)
                 .replace("$MAPS",maps)
                 .replace("$START_DATE" , start_date)
                 .replace("$START_TIME",start_time)
                 .replace("$END_DATE" , end_date)
                 .replace("$END_TIME",end_time)	
                 .replace("TIME_ZONE", time_zone)
                 .replace("$EVENT_DESCRIPTION",edescription)
                 .replace("$EVENT_WEBSITE", event_website)
                 .replace("$ORGANIZER_NAME", organizer_name)
                 .replace("$ORGANIZER_DESCRIPTION", organizer_desc)
                 .replace("$FACEBOOK", facebook)
                 .replace("$TWITTER", twitter)
                . replace("$TICKET_TYPE1",ticket_type1)
                .replace("$TICKET_NAME1", ticket_name1)
                 .replace("$TICKET_QTY1", ticket_qty1)
                 .replace("$TICKET_PRICE1", ticket_price1)
                 .replace("$THUMBNIL", "/imagedownload?id="+thumbnil)
                    . replace("$TICKET_TYPE2",ticket_type2)
                    .replace("$TICKET_NAME2", ticket_name2)
                     .replace("$TICKET_QTY2", ticket_qty2)
                     .replace("$TICKET_PRICE2", ticket_price2)
                        . replace("$TICKET_TYPE3",ticket_type3)
                        .replace("$TICKET_NAME3", ticket_name3)
                         .replace("$TICKET_QTY3", ticket_qty3)
                         .replace("$TICKET_PRICE3", ticket_price3)
                         .replace("$PUBLIC", public_page)
                         .replace("$PRIVATE", private_page)
                 ;
			out.println(str);
		
		}
			

			
			
		

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