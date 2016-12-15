package com.cyient.tableAnalysis;

import java.io.IOException;

import com.cyient.admineasy.admineasyservices.AdminEasyServicesStub.Dataset;
import com.cyient.admineasy.admineasyservices.AdminEasyServicesStub.GetTablesOfSelectedDatasetRequest;
import com.cyient.admineasy.admineasyservices.AdminEasyServicesStub.GetTablesOfSelectedDatasetRequestE;
import com.cyient.admineasy.admineasyservices.AdminEasyServicesStub.GetTablesOfSelectedDatasetResponse;
import com.cyient.admineasy.admineasyservices.AdminEasyServicesStub.GetTablesOfSelectedDatasetResponseE;
import com.cyient.admineasy.admineasyservices.AdminEasyServicesStub.TableDetails;

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
import com.google.gson.JsonArray;

/**
 * Servlet implementation class AdminTableOfSelectedDatasetServlet
 */
public class AdminTableOfSelectedDatasetServlet extends HttpServlet {
	static final Logger LOGGER = Logger
			.getLogger(AdminTableOfSelectedDatasetServlet.class);
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminTableOfSelectedDatasetServlet() {
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
		String selectedDataSet = request.getParameter("param1");
		AdminEasyServicesStub adminEasyServicesStub = new AdminEasyServicesStub(
				"http://172.16.18.75:8080/gss/web/services/AdminEasyServices/");

		LOGGER.info("Executed the getTablesOfSelectedDatasetService() method");

		GetTablesOfSelectedDatasetRequest getTablesOfSelectedDatasetRequest = new GetTablesOfSelectedDatasetRequest();
		Dataset dataset = new Dataset();
		dataset.setDataset(new NormalizedString(selectedDataSet));
		getTablesOfSelectedDatasetRequest.setSelectedDataset(dataset);
		GetTablesOfSelectedDatasetRequestE getTablesOfSelectedDatasetRequestE = new GetTablesOfSelectedDatasetRequestE();
		getTablesOfSelectedDatasetRequestE
				.setGetTablesOfSelectedDatasetRequest(getTablesOfSelectedDatasetRequest);

		GetTablesOfSelectedDatasetResponseE getTablesOfSelectedDatasetResponseE = new GetTablesOfSelectedDatasetResponseE();
		getTablesOfSelectedDatasetResponseE = adminEasyServicesStub
				.getTablesOfSelectedDatasetService(getTablesOfSelectedDatasetRequestE);

		JSONObject jsonObject;
		JSONArray selectedTabledDataSetsArray = new JSONArray();
		for (TableDetails tableDetails : getTablesOfSelectedDatasetResponseE
				.getGetTablesOfSelectedDatasetResponse().getArrayOfTables()) {
			jsonObject = new JSONObject();
			try {
				jsonObject.put("localInternalName", tableDetails
						.getInternalName().toString());
				jsonObject.put("localExternalName", tableDetails
						.getExternalName().toString());
				jsonObject
						.put("localCount", tableDetails.getCount().toString());
				jsonObject.put("localOccupiedSize", tableDetails
						.getOccupiedSize().toString());
				jsonObject.put("localBestSpaceUtilization", tableDetails
						.getBestSpaceUtilization().toString());
				jsonObject.put("localBlockOccupancyPercentile", tableDetails
						.getBlockOccupancyPercentile().toString());
				jsonObject.put("localSourceFile", tableDetails.getSourceFile()
						.toString());
				selectedTabledDataSetsArray.put(jsonObject);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		LOGGER.info(" JSON Array Response Is" + selectedTabledDataSetsArray);
		response.getWriter().println(selectedTabledDataSetsArray.toString());
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
