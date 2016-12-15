package com.cyient.superFileAnalysis;

import java.io.IOException;

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
import com.cyient.admineasy.admineasyservices.AdminEasyServicesStub.FileSystem;
import com.cyient.admineasy.admineasyservices.AdminEasyServicesStub.GetFilePathFieldsDetailsRequest;
import com.cyient.admineasy.admineasyservices.AdminEasyServicesStub.GetFilePathFieldsDetailsRequestE;
import com.cyient.admineasy.admineasyservices.AdminEasyServicesStub.GetFilePathFieldsDetailsResponseE;
import com.cyient.tableAnalysis.AdminTableAnalysisDatasetServlet;

/**
 * Servlet implementation class AdminSuperFileGetFilePathServlet
 */
public class AdminSuperFileGetFilePathServlet extends HttpServlet {

	static final Logger LOGGER = Logger
			.getLogger(AdminSuperFileGetFilePathServlet.class);
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminSuperFileGetFilePathServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("application/json");
		String dataSet = request.getParameter("param1").toLowerCase();
		AdminEasyServicesStub adminEasyServicesStub = new AdminEasyServicesStub(
				"http://172.16.18.75:8080/gss/web/services/AdminEasyServices/");

		GetFilePathFieldsDetailsRequestE getFilePathFieldsDetailsRequestE = new GetFilePathFieldsDetailsRequestE();
		GetFilePathFieldsDetailsRequest getFilePathFieldsDetailsRequest = new GetFilePathFieldsDetailsRequest();
		Dataset dataset = new Dataset();
		dataset.setDataset(new NormalizedString(dataSet));
		getFilePathFieldsDetailsRequest.setSelectedDataset(dataset);
		getFilePathFieldsDetailsRequestE
				.setGetFilePathFieldsDetailsRequest(getFilePathFieldsDetailsRequest);
		GetFilePathFieldsDetailsResponseE getFieldsDetailsResponseE = new GetFilePathFieldsDetailsResponseE();
		getFieldsDetailsResponseE = adminEasyServicesStub
				.getFilePathFieldsDetailsService(getFilePathFieldsDetailsRequestE);
		System.out.println(getFieldsDetailsResponseE);
		getFieldsDetailsResponseE.getGetFilePathFieldsDetailsResponse()
				.getFileSystems();
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonCurrentAlternatives;
		JSONObject jsonFileSystems;
		JSONObject jsonFilePath;

		for (FileSystem fileSystem : getFieldsDetailsResponseE
				.getGetFilePathFieldsDetailsResponse().getFileSystems()) {
			jsonFileSystems = new JSONObject();
			try {
				jsonFileSystems.put("localFileSystems", fileSystem
						.getFileSystem().toString());
				jsonArray.put(jsonFileSystems);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		jsonFilePath = new JSONObject();
		jsonCurrentAlternatives = new JSONObject();
		try {
			jsonCurrentAlternatives.put("localCurrentAlternative",
					getFieldsDetailsResponseE
							.getGetFilePathFieldsDetailsResponse()
							.getCurrentAlternative());
			jsonFilePath.put("localSelectedfilePath", getFieldsDetailsResponseE
					.getGetFilePathFieldsDetailsResponse()
					.getSelectedfilePath().toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		jsonArray.put(jsonCurrentAlternatives);
		jsonArray.put(jsonFilePath);
		LOGGER.info("getFilePathFieldsDetailsService() Response is "
				+ jsonArray.toString());

		response.getWriter().println(jsonArray.toString());

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
