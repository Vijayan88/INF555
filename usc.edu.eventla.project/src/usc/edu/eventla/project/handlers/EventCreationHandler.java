package usc.edu.eventla.project.handlers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.bson.Document;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSInputFile;

public class EventCreationHandler extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String title = request.getParameter("event_title");
		String event_category = request.getParameter("event_category");
		String venue = request.getParameter("venue");
		String address1 = request.getParameter("address1");
		String address2 = request.getParameter("address2");
		String city_name = request.getParameter("city_name");
		String pin_code = request.getParameter("pin_code");
		String status = request.getParameter("statusId");
		String start_date = request.getParameter("start_date");
		String start_time = request.getParameter("start_time");
		String end_date = request.getParameter("end_date");
		String end_time = request.getParameter("end_time");
		//String event_image = request.getParameter("event_image");
		String description = request.getParameter("event_description");
		String event_website = request.getParameter("event_website");
		String organizer_name = request.getParameter("organiser_name");
		String organiser_description = request.getParameter("organiser_description");
		String facebook = request.getParameter("facebook_link");
		String twitter_link= request.getParameter("twitter_link");
		String ticket_type1 = request.getParameter("ticket_type1");
		String ticket_name1 = request.getParameter("ticket_name1");
		String quantity_present1 = request.getParameter("quantity_present1");
		String ticket_price1 = request.getParameter("ticket_price1");
		String ticket_type2 = request.getParameter("ticket_type2");
		String ticket_name2 = request.getParameter("ticket_name2");
		String quantity_present2 = request.getParameter("quantity_present2");
		String ticket_price2 = request.getParameter("ticket_price2");
		String ticket_type3 = request.getParameter("ticket_type1");
		String ticket_name3 = request.getParameter("ticket_name3");
		String quantity_present3 = request.getParameter("quantity_present3");
		String ticket_price3 = request.getParameter("ticket_price3");
		String event_type= request.getParameter("event_type");
		String maps = request.getParameter("maps");
	
		/*out1.println(title);
		out1.println(event_type);
		out1.println(event_category);
		
		out1.println(venue) ;
		out1.println(address1); 
		out1.println(address2) ;
		out1.println(city_name) ;
		out1.println(pin_code) ;
		
		out1.println(start_date);
		out1.println(start_time) ;
		out1.println(end_date) ;
		out1.println(end_time) ;
		//String event_image = request.getParameter("event_image");
		out1.println(description); 
		out1.println(event_website); 
		out1.println(organizer_name);
		out1.println(organiser_description);
		out1.println(facebook) ;
		out1.println( twitter_link);
		out1.println( ticket_type1);
		out1.println(ticket_name1) ;
		out1.println(quantity_present1);
		out1.println(ticket_price1) ;
		out1.println(ticket_type2) ;
		out1.println(ticket_name2); 
		out1.println(quantity_present2); 
		out1.println(ticket_price2); 
		out1.println(ticket_type3); 
		out1.println(ticket_name3 );
		out1.println(quantity_present3); 
		out1.println(ticket_price3 );
		*/
		String imageName = title.replace(" ", "").toLowerCase()+"_"+start_date;
		String user ="";
		Cookie cookie = null;
		Cookie[] cookies = null;
		cookies = request.getCookies();
		String loginContents = "";
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				cookie = cookies[i];
				if (cookie.getName().equals("username")) {
				 user = cookie.getValue();
					break;
				}
			}
		}
		PrintWriter out1 = response.getWriter();
		
	    MongoClient mongoClient = new MongoClient();

		MongoDatabase database = mongoClient.getDatabase("eventsla");
		MongoCollection<Document> collection = database.getCollection("CreateEvent");

		Document doc = new Document("event_title", title)
				.append("username", user)
				.append("event_type", event_type)
				.append("event_category",event_category).
				append("address", new Document("venue" ,venue)
                .append("address1", address1)
                .append("address2", address2)
                .append("city_name", city_name)
                .append("state", "CA")
                .append("pin_code", pin_code)
                .append("maps", maps)
                .append("start_date",start_date)
                .append("start_time",start_time))
                .append("event_description", description).append("city_name",city_name)
				.append("end_date",end_date)
				.append("end_time", end_time)
				.append("event_website",event_website)
				.append("organiser_name",organizer_name)
			    .append("facebook_link",facebook)
				.append("twitter_link", twitter_link)
				.append("ticket_1", new Document("ticket_type1",ticket_type1)
				.append("quantity_present1",quantity_present1)
				.append("ticket_price1",ticket_price1)
				.append("ticket_name1",ticket_name1)) 
				.append("ticket_2",new Document("ticket_type2",ticket_type2)
						.append("quantity_present2",quantity_present2)
						.append("ticket_price2",ticket_price2)
						.append("ticket_name2",ticket_name2)) 
				.append("ticket_3",new Document( "ticket_type3",ticket_type3)
						.append("quantity_present3",quantity_present3)
						.append("ticket_price3",ticket_price3)
						.append("ticket_name3",ticket_name3))
				.append("image_name" , imageName).append("status", status);
		collection.insertOne(doc); 
	//	response.sendRedirect("eventdesc.html");

		
 // image key : image_name value :  title+"_"+start_date
		// 
		final Part filePart = request.getPart("file");

	    OutputStream out = null;
	    InputStream filecontent = null;
	    final PrintWriter writer = response.getWriter();
	    try {
	        out = new FileOutputStream(new File(
	                 "newfile.jpg"));
	        filecontent = filePart.getInputStream();

	        int read = 0;
	        final byte[] bytes = new byte[1024];

	        while ((read = filecontent.read(bytes)) != -1) {
	            out.write(bytes, 0, read);
	        }
	    } catch (FileNotFoundException fne) {
	        writer.println("You either did not specify a file to upload or are "
	                + "trying to upload a file to a protected or nonexistent "
	                + "location.");
	        writer.println("<br/> ERROR: " + fne.getMessage());

	    } if (out != null) {
            out.close();
        }
	    
	
	    

		
	    Mongo mongo = new Mongo("localhost", 27017);
		DB db = mongo.getDB("eventsla");
	//	DBCollection collection = db.getCollection("EventDetails");
		File imageFile = new File("newfile.jpg");
		// create a "photo" namespace
		GridFS gfsPhoto = new GridFS(db, "photo");

		// get image file from local drive
		GridFSInputFile gfsFile = gfsPhoto.createFile(imageFile);

		// set a new filename for identify purpose
		gfsFile.setFilename(imageName);

		// save the image file into mongoDB
		gfsFile.save();

			InputStream in;
		in = getServletContext().getResourceAsStream("/ireg.html");
		BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
		StringBuilder sb = new StringBuilder();
		String line = null;

		while ((line = reader.readLine()) != null) {
			sb.append(line + "\n");
		}
		String logContents = loginDetails("loginuser.txt");
		writer.println(sb.toString().replace("$LOGIN_DETAILS", logContents).replace("$USER", user));
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


}
