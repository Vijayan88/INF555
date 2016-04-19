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

public class StatusUpdateHandler extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String eve_status = request.getParameter("statusId");
		String eve_name = request.getParameter("evename");

		// to let users from cssl to test freely
		Mongo mongoClient = new Mongo("localhost", 27017);
		// String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
		DB database = mongoClient.getDB("eventsla");
		DBCollection collection = database.getCollection("CreateEvent");
		
		PrintWriter out = response.getWriter();
		InputStream in;
		
		BasicDBObject newDocument = new BasicDBObject();
		newDocument.append("$set", new BasicDBObject().append("status", eve_status));
		BasicDBObject searchQuery = new BasicDBObject().append("p_key", eve_name);
		collection.update(searchQuery, newDocument);
	    
		response.sendRedirect("/adminlist");
  
	}

	public static void main(String[] args) {
		
		// to let users from cssl to test freely
		Mongo mongoClient = new Mongo("localhost", 27017);
		// String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
		DB database = mongoClient.getDB("eventsla");
		DBCollection collection = database.getCollection("CreateEvent");
		
		
		BasicDBObject newDocument = new BasicDBObject();
		newDocument.append("$set", new BasicDBObject().append("status", "save"));
		BasicDBObject searchQuery = new BasicDBObject().append("p_key", "losangelesclippersvs.portlandtrailblazers_2016-04-20");
		collection.update(searchQuery, newDocument);
	    
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