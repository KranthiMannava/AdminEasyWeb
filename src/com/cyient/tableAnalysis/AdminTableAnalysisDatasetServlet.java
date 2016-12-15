package com.cyient.tableAnalysis;

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
import com.cyient.admineasy.admineasyservices.AdminEasyServicesStub.GetDatasetsRequest;
import com.cyient.admineasy.admineasyservices.AdminEasyServicesStub.GetDatasetsRequestE;
import com.cyient.admineasy.admineasyservices.AdminEasyServicesStub.GetDatasetsResponseE;

/**
 * Servlet implementation class AdminTableAnalysisDatasetServlet
 */
public class AdminTableAnalysisDatasetServlet extends HttpServlet {

	static final Logger LOGGER = Logger
			.getLogger(AdminTableAnalysisDatasetServlet.class);
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminTableAnalysisDatasetServlet() {
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
		AdminEasyServicesStub adminEasyServicesStub = new AdminEasyServicesStub(
				"http://172.16.18.75:8080/gss/web/services/AdminEasyServices/");

		LOGGER.info("Executed the ShowDesignsDetailsService() request method");

		GetDatasetsRequest datasetsRequest = new GetDatasetsRequest();
		datasetsRequest.setGetDatasets(new NormalizedString("Hello"));
		GetDatasetsRequestE datasetsRequestE = new GetDatasetsRequestE();
		datasetsRequestE.setGetDatasetsRequest(datasetsRequest);
		JSONArray jsonGridData = new JSONArray();
		JSONObject jsonObject;
		GetDatasetsResponseE getDatasetsResponseE = adminEasyServicesStub
				.getDatasetsService(datasetsRequestE);
		for (Dataset iterable_element : getDatasetsResponseE
				.getGetDatasetsResponse().getArrayOfDatasets()) {
			jsonObject=new JSONObject();
			try {
				jsonObject.put("localDataset", iterable_element.getDataset().toString());
			} catch (JSONException e) {
				e.printStackTrace();
			}
			jsonGridData.put(jsonObject);
		}
		response.getWriter().println(jsonGridData);

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
