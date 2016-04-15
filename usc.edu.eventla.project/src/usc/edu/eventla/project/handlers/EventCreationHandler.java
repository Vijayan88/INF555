package usc.edu.eventla.project.handlers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.bson.Document;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
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
		String state = request.getParameter("state");
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
		PrintWriter out = response.getWriter();
		out.println(title);
		out.println(event_type);
		out.println(event_category);
		
		out.println(venue) ;
		out.println(address1); 
		out.println(address2) ;
		out.println(city_name) ;
		out.println(pin_code) ;
		out.println(state);
		out.println(start_date);
		out.println(start_time) ;
		out.println(end_date) ;
		out.println(end_time) ;
		//String event_image = request.getParameter("event_image");
		out.println(description); 
		out.println(event_website); 
		out.println(organizer_name);
		out.println(organiser_description);
		out.println(facebook) ;
		out.println( twitter_link);
		out.println( ticket_type1);
		out.println(ticket_name1) ;
		out.println(quantity_present1);
		out.println(ticket_price1) ;
		out.println(ticket_type2) ;
		out.println(ticket_name2); 
		out.println(quantity_present2); 
		out.println(ticket_price2); 
		out.println(ticket_type3); 
		out.println(ticket_name3 );
		out.println(quantity_present3); 
		out.println(ticket_price3 );
		
		
	/*	MongoClient mongoClient = new MongoClient();

		MongoDatabase database = mongoClient.getDatabase("eventsla");
		MongoCollection<Document> collection = database.getCollection("CreateEvent");

		Document doc = new Document("event_title", title).append("address", new Document("venue" ,venue)
                .append("address1", address1)
                .append("address2", address2)
                .append("city_name", city_name)
                .append("state", state)
                .append("pin_code", pin_code)
                .append("start_date",start_date)
                .append("start_time",start_time))
				.append("event_description", description);
		collection.insertOne(doc); */
	//	response.sendRedirect("eventdesc.html");
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/*final Part filePart = request.getPart("file");
	    final String fileName = getFileName(filePart);

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
	        writer.println("New file " + fileName + " created at " + "/tmp");
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

		String newFileName = "mkyong-java-image";

		File imageFile = new File("newfile.jpg");

		// create a "photo" namespace
		GridFS gfsPhoto = new GridFS(db, "photo");

		// get image file from local drive
		GridFSInputFile gfsFile = gfsPhoto.createFile(imageFile);

		// set a new filename for identify purpose
		gfsFile.setFilename(newFileName);

		// save the image file into mongoDB
		gfsFile.save();
		
		GridFSDBFile imageForOutput = gfsPhoto.findOne(newFileName);

		// save it into a new image file
		imageForOutput.writeTo("/Users/vijayan/Desktop/test.jpg");
	}
	private String getFileName(final Part part) {
	  return "/tmp";
*/
}
}
