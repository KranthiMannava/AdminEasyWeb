package com.cyient.superFileAnalysis;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.axis2.databinding.types.NormalizedString;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.cyient.admineasy.admineasyservices.AdminEasyServicesStub;
import com.cyient.admineasy.admineasyservices.AdminEasyServicesStub.Dataset;
import com.cyient.admineasy.admineasyservices.AdminEasyServicesStub.FileDetails;
import com.cyient.admineasy.admineasyservices.AdminEasyServicesStub.FilePath;
import com.cyient.admineasy.admineasyservices.AdminEasyServicesStub.GetAllFilesDetailsRequest;
import com.cyient.admineasy.admineasyservices.AdminEasyServicesStub.GetAllFilesDetailsRequestE;
import com.cyient.admineasy.admineasyservices.AdminEasyServicesStub.GetAllFilesDetailsResponseE;

public class AdminSuperFileGetAllFilesDetailsServlet extends HttpServlet {
	static final Logger LOGGER = Logger
			.getLogger(AdminSuperFileGetAllFilesDetailsServlet.class);
	/**
	 * Constructor of the object.
	 */
	public AdminSuperFileGetAllFilesDetailsServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");

		String selectedDataSet = request.getParameter("param1").toLowerCase();
		String filepath = request.getParameter("param2");
		AdminEasyServicesStub adminEasyServicesStub = new AdminEasyServicesStub(
				"http://172.16.18.75:8080/gss/web/services/AdminEasyServices/");

		GetAllFilesDetailsRequest getAllFilesDetailsRequest = new GetAllFilesDetailsRequest();
		GetAllFilesDetailsRequestE getAllFilesDetailsRequestE = new GetAllFilesDetailsRequestE();
		GetAllFilesDetailsResponseE getAllFilesDetailsResponseE = new GetAllFilesDetailsResponseE();

		Dataset dataSet = new Dataset();
		dataSet.setDataset(new NormalizedString(selectedDataSet));
		FilePath filePath = new FilePath();
		filePath.setFilePath(new NormalizedString(filepath));
		getAllFilesDetailsRequest.setSelectedDataset(dataSet);
		getAllFilesDetailsRequest.setSelectedfilePath(filePath);
		getAllFilesDetailsRequestE
				.setGetAllFilesDetailsRequest(getAllFilesDetailsRequest);
		getAllFilesDetailsResponseE = adminEasyServicesStub
				.getAllFilesDetailsService(getAllFilesDetailsRequestE);
		JSONArray getFileDetailsJSONResponse = new JSONArray();
		JSONObject jsonObject;
		for (FileDetails fileDetails : getAllFilesDetailsResponseE
				.getGetAllFilesDetailsResponse().getArrayOfFileDetails()) {
			try {
				jsonObject = new JSONObject();
				jsonObject.put("fileName", fileDetails.getFileName());
				jsonObject.put("superFile", fileDetails.getSuperFile());
				jsonObject.put("totalComponents",
						fileDetails.getTotalComponents());
				jsonObject.put("capacityGB", fileDetails.getTotalComponents());
				jsonObject.put("usedGB", fileDetails.getTotalComponents());
				jsonObject.put("freeGB", fileDetails.getTotalComponents());
				jsonObject.put("status", fileDetails.getTotalComponents());
				jsonObject.put("usedPercent", fileDetails.getTotalComponents());
				getFileDetailsJSONResponse.put(jsonObject);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		LOGGER.info("getAllFilesDetailsService() method response is"+getFileDetailsJSONResponse);
		
		response.getWriter().println(getFileDetailsJSONResponse.toString());
		System.out.println();

	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the POST method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
