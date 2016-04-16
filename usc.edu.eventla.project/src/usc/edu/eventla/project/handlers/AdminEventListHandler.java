package usc.edu.eventla.project.handlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

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

public class AdminEventListHandler extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	
		Mongo mongoClient = new Mongo("localhost", 27017);
		DB database = mongoClient.getDB("eventsla");
		DBCollection collection = database.getCollection("Contact");
		DBCursor cursor = collection.find();
		long events_cnt = collection.getCount();
		String name = null,email="",phone="",date="",time="",message="";
		if (cursor != null) {
			DBObject res = cursor.next();
			name = (String) res.get("name");
			 email = (String) res.get("email");
			 phone = (String) res.get("phone");
		     date = (String) res.get("date");
		     time = (String) res.get("time");
		     message = (String) res.get("message");
		}
		mongoClient.close();
		//
		PrintWriter out = response.getWriter();
		InputStream in;
		in = getServletContext().getResourceAsStream("admin_dash1.txt");
		BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
		StringBuilder sb = new StringBuilder();
		String line = null;
		while ((line = reader.readLine()) != null) {
			sb.append(line + "\n");
		}

		
		// mongodb
		//for (int i = 0; i <= events_cnt; i++) {
			in = getServletContext().getResourceAsStream("admin_dash2.txt");
			reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			sb = new StringBuilder();
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			out.println(sb.toString());
			
		
		for (int i = 0; i < events_cnt; i++) {
		in = getServletContext().getResourceAsStream("admin_dash3.txt");
		reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
		sb = new StringBuilder();
		while ((line = reader.readLine()) != null) {
			sb.append(line + "\n");
		}
		
		out.println(sb.toString().replace("$NAME", name).replace("$EMAIL", email).replace("$DATE", date).replace("$TIME", time).replace("$MESSAGE", message));
        
	}
	}

	
}