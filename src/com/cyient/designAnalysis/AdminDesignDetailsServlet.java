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
import com.cyient.admineasy.admineasyservices.AdminEasyServicesStub.DesignFilter;
import com.cyient.admineasy.admineasyservices.AdminEasyServicesStub.DesignOption;
import com.cyient.admineasy.admineasyservices.AdminEasyServicesStub.DesignStatus;
import com.cyient.admineasy.admineasyservices.AdminEasyServicesStub.FromDate;
import com.cyient.admineasy.admineasyservices.AdminEasyServicesStub.ShowAllDesignsOfSelected;
import com.cyient.admineasy.admineasyservices.AdminEasyServicesStub.ShowDesignsDetailsRequest;
import com.cyient.admineasy.admineasyservices.AdminEasyServicesStub.ShowDesignsDetailsRequestE;
import com.cyient.admineasy.admineasyservices.AdminEasyServicesStub.ShowDesignsDetailsResponseE;
import com.cyient.admineasy.admineasyservices.AdminEasyServicesStub.ToDate;

public class AdminDesignDetailsServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static final Logger LOGGER = Logger.getLogger(AdminDesignDataServlet.class);

	/**
	 * Constructor of the object.
	 */
	public AdminDesignDetailsServlet() {
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

		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		String selectedDesignOption = request.getParameter("param1");
		String fromDateDesign = request.getParameter("param2");
		String toDateDesign = request.getParameter("param3");
		String designFilterAnalysis = request.getParameter("param4");
		String rowDesignStatusName = request.getParameter("param5");

		AdminEasyServicesStub adminEasyServicesStub1 = new AdminEasyServicesStub(
				"http://172.16.18.75:8080/gss/web/services/AdminEasyServices/");

		LOGGER.info("Executed the ShowDesignsDetailsService() request method");

		ShowDesignsDetailsRequestE showDesignsDetailsRequest = new ShowDesignsDetailsRequestE();
		ShowDesignsDetailsRequest designsDetailsRequest = new ShowDesignsDetailsRequest();

		DesignFilter designFilter = new DesignFilter();
		designFilter
				.setDesignFilter(new NormalizedString(designFilterAnalysis));
		designsDetailsRequest.setDesignFilter(designFilter);

		DesignOption designOption = new DesignOption();
		designOption
				.setDesignOption(new NormalizedString(selectedDesignOption));
		designsDetailsRequest.setDesignOption(designOption);

		ToDate toDate = new ToDate();
		toDate.setToDate(new NormalizedString(toDateDesign));
		designsDetailsRequest.setToDate(toDate);

		FromDate fromDate = new FromDate();
		fromDate.setFromDate(new NormalizedString(fromDateDesign));
		designsDetailsRequest.setFromDate(fromDate);

		DesignStatus designStatus = new DesignStatus();
		designStatus.setDesignStatus(new NormalizedString(rowDesignStatusName));
		designsDetailsRequest.setDesignStatus(designStatus);

		showDesignsDetailsRequest
				.setShowDesignsDetailsRequest(designsDetailsRequest);

		ShowDesignsDetailsResponseE showDesignsDetailsResponseE = new ShowDesignsDetailsResponseE();
		showDesignsDetailsResponseE = adminEasyServicesStub1
				.showDesignsDetailsService(showDesignsDetailsRequest);
		System.out.println(showDesignsDetailsResponseE.toString());

		ShowAllDesignsOfSelected showAllDesignsOfSelected = new ShowAllDesignsOfSelected();
		JSONObject obj;
		JSONArray arr = new JSONArray();
		for (ShowAllDesignsOfSelected showAllDesigns : showDesignsDetailsResponseE
				.getShowDesignsDetailsResponse().getArrayOfallDesignsInfo()) {
			
			obj = new JSONObject();

			try {
				obj.put("designId", showAllDesigns.getDesignId().toString());
				obj.put("designName", showAllDesigns.getDesignName().toString());
				obj.put("projectName", showAllDesigns.getProjectName()
						.toString());
				obj.put("designStatus", showAllDesigns.getDesignStatus()
						.toString());
				obj.put("owner", showAllDesigns.getOwner().toString());
				obj.put("createdOn", showAllDesigns.getCreatedOn().toString());
				obj.put("lastChangedOn", showAllDesigns.getLastChangedOn()
						.toString());
				obj.put("projectType", showAllDesigns.getProjectType()
						.toString());
				obj.put("age", showAllDesigns.getAge().toString());
				LOGGER.info("JSON Object data is"+obj.toString());
				arr.put(obj);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			LOGGER.info(" JSON Array Response Is" + arr);

		}

		response.getWriter().println(arr);

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
