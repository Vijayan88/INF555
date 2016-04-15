package usc.edu.eventla.project.handlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EventListHandler extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		InputStream in;
		in = getServletContext().getResourceAsStream("eventlisttemplate1.txt");
		BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
		StringBuilder sb = new StringBuilder();
		String line = null;
		while ((line = reader.readLine()) != null) {
			sb.append(line + "\n");
		}
		out.println(sb.toString());

	
	
	// mongodb
	for(int i = 0; i < 4 ; i ++){
		in = getServletContext().getResourceAsStream("eventlisttemplate2.txt");
        reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
		sb = new StringBuilder();
		while ((line = reader.readLine()) != null) {
			sb.append(line + "\n");
		}
		out.println(sb.toString() );
		if(i != 3)
			out.println("<hr>");
	}
	in = getServletContext().getResourceAsStream("/eventlisttemplate3.txt");
    reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
	sb = new StringBuilder();
	while ((line = reader.readLine()) != null) {
		sb.append(line + "\n");
	}
	out.println(sb.toString());
	
}
}