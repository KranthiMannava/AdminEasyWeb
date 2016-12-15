package com.cyient.designAnalysis;

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
import com.cyient.admineasy.admineasyservices.AdminEasyServicesStub.AlternateDetails;
import com.cyient.admineasy.admineasyservices.AdminEasyServicesStub.DatasetName;
import com.cyient.admineasy.admineasyservices.AdminEasyServicesStub.DesignId;
import com.cyient.admineasy.admineasyservices.AdminEasyServicesStub.ShowAlternativeDetailsDesignRequest;
import com.cyient.admineasy.admineasyservices.AdminEasyServicesStub.ShowAlternativeDetailsDesignRequestE;
import com.cyient.admineasy.admineasyservices.AdminEasyServicesStub.ShowAlternativeDetailsDesignResponse;
import com.cyient.admineasy.admineasyservices.AdminEasyServicesStub.ShowAlternativeDetailsDesignResponseE;

public class AdminDesignAlternativeDetailsServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static final Logger LOGGER = Logger.getLogger(AdminDesignDataServlet.class);

	/**
	 * Constructor of the object.
	 */
	public AdminDesignAlternativeDetailsServlet() {
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
		PrintWriter out = response.getWriter();

		String alternativeDesignId = request.getParameter("param1");
		AdminEasyServicesStub adminEasyServicesStub1 = new AdminEasyServicesStub(
				"http://172.16.18.75:8080/gss/web/services/AdminEasyServices/");

		LOGGER.info("Executed the ShowDesignsDetailsService() request method");

		ShowAlternativeDetailsDesignRequest showAlternativeDetailsDesignRequest = new ShowAlternativeDetailsDesignRequest();
		ShowAlternativeDetailsDesignRequestE alternativeDetailsDesignRequestE = new ShowAlternativeDetailsDesignRequestE();

		DesignId designId = new DesignId();
		designId.setDesignId(new NormalizedString(alternativeDesignId));
		showAlternativeDetailsDesignRequest.setDesignId(designId);

		alternativeDetailsDesignRequestE
				.setShowAlternativeDetailsDesignRequest(showAlternativeDetailsDesignRequest);

		ShowAlternativeDetailsDesignResponseE alternativeDetailsDesignResponseE = new ShowAlternativeDetailsDesignResponseE();

		alternativeDetailsDesignResponseE = adminEasyServicesStub1
				.showAlternativeDetailsDesignService(alternativeDetailsDesignRequestE);
		ShowAlternativeDetailsDesignResponse alternativeDetailsDesignResponse = new ShowAlternativeDetailsDesignResponse();
		// alternativeDetailsDesignResponse.setShowAlternativeDetailsDesignResponse(alternativeDetailsDesignResponseE);
		//
		alternativeDetailsDesignResponse = alternativeDetailsDesignResponseE
				.getShowAlternativeDetailsDesignResponse();

		AlternateDetails alternateDetails=alternativeDetailsDesignResponse.getAlternateDetails();
		JSONObject jsonObject;
		JSONArray jsonArray=new JSONArray();
		try {
			jsonObject=new JSONObject();
			jsonObject.put("datasetName", alternateDetails.getDatasetName().toString());
			jsonObject.put("alternatePath", alternateDetails.getAlternativePath().toString());
			jsonObject.put("exists", alternateDetails.getExists().toString());
			jsonArray.put(jsonObject);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LOGGER.info("getService Response is "+jsonArray);
		response.getWriter().println(jsonArray);		
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
