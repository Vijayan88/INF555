package usc.edu.eventla.project.handlers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;

public class ImageDownloadHandler extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		 String imageId = request.getParameter("id");

	        // Check if ID is supplied to the request.
	        if (imageId == null) {
	            // Do your thing if the ID is not supplied to the request.
	            // Throw an exception, or send 404, or show default/warning image, or just ignore it.
	            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
	            return;
	        }
	     // to let users from cssl to test freely
			Mongo mongoClient = new Mongo("localhost", 27017);
			// String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
			DB database = mongoClient.getDB("eventsla");
	        GridFS gfsPhoto = new GridFS(database, "photo");
			GridFSDBFile imageForOutput = gfsPhoto.findOne(imageId);

			InputStream is = imageForOutput.getInputStream();
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int nRead;
            byte[] data = new byte[16384];
            while ((nRead = is.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            buffer.flush();
            byte[]imagenEnBytes = buffer.toByteArray();
            response.setHeader("Accept-ranges","bytes");
            response.setContentType( "image/jpeg" );
            response.setContentLength(imagenEnBytes.length);
            response.setHeader("Expires","0");
            response.setHeader("Cache-Control","must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Content-Description","File Transfer");
            response.setHeader("Content-Transfer-Encoding:","binary");

            OutputStream out = response.getOutputStream();
            out.write( imagenEnBytes );
            out.flush();
            out.close();
		
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		 String imageId = request.getParameter("id");

	        // Check if ID is supplied to the request.
	        if (imageId == null) {
	            // Do your thing if the ID is not supplied to the request.
	            // Throw an exception, or send 404, or show default/warning image, or just ignore it.
	            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
	            return;
	        }
	     // to let users from cssl to test freely
			Mongo mongoClient = new Mongo("localhost", 27017);
			// String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
			DB database = mongoClient.getDB("eventsla");
	        GridFS gfsPhoto = new GridFS(database, "photo");
			GridFSDBFile imageForOutput = gfsPhoto.findOne(imageId);

			InputStream is = imageForOutput.getInputStream();
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int nRead;
            byte[] data = new byte[16384];
            while ((nRead = is.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            buffer.flush();
            byte[]imagenEnBytes = buffer.toByteArray();
            response.setHeader("Accept-ranges","bytes");
            response.setContentType( "image/jpeg" );
            response.setContentLength(imagenEnBytes.length);
            response.setHeader("Expires","0");
            response.setHeader("Cache-Control","must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Content-Description","File Transfer");
            response.setHeader("Content-Transfer-Encoding:","binary");

            OutputStream out = response.getOutputStream();
            out.write( imagenEnBytes );
            out.flush();
            out.close();
		
	}

}
