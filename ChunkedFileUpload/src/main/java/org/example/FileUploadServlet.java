package org.example;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.soap.AddressingFeature.Responses;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

public class FileUploadServlet extends HttpServlet {

	private boolean isMultipart;
	private String filePath;
	private int maxFileSize = 40*1024 * 1024;
	private int maxMemSize = 4 * 1024;
	private File file ;

	private Map<String, Map<Integer, FileItem>> map = new HashMap<String, Map<Integer,FileItem>>();
	public void init() {
		// Get the file location where it would be stored.
		filePath = getServletContext().getInitParameter("file-upload");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {
		// Check that we have a file upload request
		java.io.PrintWriter out = response.getWriter();
		// Create a new file upload handler
		ServletFileUpload upload = null;
		try {
			//isMultipart = ServletFileUpload.isMultipartContent(request);
			response.setContentType("application/json");
			
			
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// maximum size that will be stored in memory
			factory.setSizeThreshold(maxMemSize);
			// Location to save data that is larger than maxMemSize.
			factory.setRepository(new File("c:\\temp"));

			upload = new ServletFileUpload(factory);
			// maximum file size to be uploaded.
			upload.setSizeMax(maxFileSize);
			/*TestProgressListener testProgressListener = new TestProgressListener();
			upload.setProgressListener(testProgressListener);*/
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			// Parse the request to get file items.
//			List<FileItem> fileItems = upload.parseRequest(request);

			// Process the uploaded file items
//			Iterator<FileItem> i = fileItems.iterator();

			
//			while (i.hasNext()) {
//				final FileItem fi = (FileItem) i.next();
//				if (!fi.isFormField()) {
					// Get the uploaded file parameters
					String fileUuid = request.getHeader("Content-Id");
					String fileName = request.getHeader("Content-Name");
					int start = Integer.parseInt(request.getHeader("Content-Start"));
					long size = Long.parseLong(request.getHeader("Content-Size"));
					String filep = filePath + fileUuid;
					FileOutputStream fos = null;
					try {
						file = new File(filep);
						fos = new FileOutputStream(file, start!=0);
						int noOfBytes = 0;
						byte[] buffer = new byte[1024];
						InputStream is = request.getInputStream();
						
						while((noOfBytes = is.read(buffer)) != -1){
							fos.write(buffer, 0, noOfBytes);
						}
						
					} catch (Exception e) {
						e.printStackTrace();
					}finally{
						fos.flush();
						fos.close();
					}
					long uploaded = file.length();
					if(uploaded == size){
						File file2 = new File(filePath + fileName);
						if(file2.exists()){
							file2.delete();
						}
						file.renameTo(file2);
					}
					
					FileInfo fileInfo = new FileInfo(fileUuid, fileName, size, uploaded);
					
					ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
					String json = ow.writeValueAsString(fileInfo);
					out.println(json);
					out.close();
//				}
//			}
		} catch (Exception ex) {
			response.sendError(500);
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {

		String uuid = request.getParameter("uuid");
		String name =  request.getParameter("name");
		long size = Long.parseLong(request.getParameter("size"));
		String type = request.getParameter("type");
		
		long uploaded = 0;
		
		if(StringUtils.isBlank(uuid)){
			uuid = UUID.randomUUID().toString();
		}else{
			String filep = filePath + uuid;
			file = new File(filep);
			if(file.exists()){
				uploaded = file.length();
			}
		}
		FileInfo fileInfo = new FileInfo(uuid, name, size, uploaded);
		PrintWriter out = response.getWriter();
		
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(fileInfo);
		out.println(json);
		out.close();
	}
}
