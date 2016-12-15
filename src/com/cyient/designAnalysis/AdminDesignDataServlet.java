package com.cyient.designAnalysis;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.axis2.AxisFault;
import org.apache.axis2.databinding.types.NormalizedString;
import org.apache.log4j.Logger;

import com.cyient.admineasy.admineasyservices.AdminEasyServicesStub;
import com.cyient.admineasy.admineasyservices.AdminEasyServicesStub.CategorizedDesigns;
import com.cyient.admineasy.admineasyservices.AdminEasyServicesStub.DesignFilter;
import com.cyient.admineasy.admineasyservices.AdminEasyServicesStub.DesignOption;
import com.cyient.admineasy.admineasyservices.AdminEasyServicesStub.FromDate;
import com.cyient.admineasy.admineasyservices.AdminEasyServicesStub.GetDesignsRequest;
import com.cyient.admineasy.admineasyservices.AdminEasyServicesStub.GetDesignsRequestE;
import com.cyient.admineasy.admineasyservices.AdminEasyServicesStub.GetDesignsResponse;
import com.cyient.admineasy.admineasyservices.AdminEasyServicesStub.ToDate;
import com.google.gson.Gson;

//import com.google.gson.Gson;

/**
 * Servlet implementation class AdminDesignDataServlet
 */
//@WebServlet("/AdminDesignDataServlet")
public class AdminDesignDataServlet extends HttpServlet {

	static final Logger LOGGER = Logger.getLogger(AdminDesignDataServlet.class);

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminDesignDataServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");

		String fromDateDesign = request.getParameter("param1");
		String toDateDesign = request.getParameter("param2");
		String selectedDesignOption = request.getParameter("param3");
		String designFilterAnalysis = request.getParameter("param4");
		System.out.println(designFilterAnalysis);

		try {
			AdminEasyServicesStub adminEasyServicesStub = new AdminEasyServicesStub("http://172.16.18.75:8080/gss/web/services/AdminEasyServices/");
			
			LOGGER.info("Executed the getService() request method");
			
			GetDesignsRequestE getDesignsRequest = new GetDesignsRequestE();
			GetDesignsRequest param = new GetDesignsRequest();

			DesignFilter designFilter = new DesignFilter();
			designFilter.setDesignFilter(new NormalizedString(
					designFilterAnalysis));

			DesignOption designOption = new DesignOption();
			designOption.setDesignOption(new NormalizedString(
					selectedDesignOption));

			FromDate fromDate = new FromDate();
			fromDate.setFromDate(new NormalizedString(fromDateDesign));

			ToDate toDate = new ToDate();
			toDate.setToDate(new NormalizedString(toDateDesign));

			param.setDesignFilter(designFilter);
			param.setDesignOption(designOption);
			param.setFromDate(fromDate);
			param.setToDate(toDate);
			getDesignsRequest.setGetDesignsRequest(param);
			//GetDesignsResponse designsResponse = adminEasyServicesStub.getDesignsService(getDesignsRequest);
			GetDesignsResponse designsResponse=adminEasyServicesStub.getDesignsService(getDesignsRequest);
			List<DesignGridData> list = new ArrayList<DesignGridData>();
			for (CategorizedDesigns categorizedDesigns : designsResponse
					.getGetDesignsResponse().getArrayOfcategorizedDesignsName()) {
				DesignGridData designGridData = new DesignGridData();
				designGridData.setDesignStatus(categorizedDesigns
						.getDesignStatus().toString());
				designGridData.setNoOfDesigns(categorizedDesigns
						.getNoOfDesigns().toString());
				list.add(designGridData);
			}

			String jsonGridData = new Gson().toJson(list);
			LOGGER.info("getService Response is "+jsonGridData);
			response.getWriter().println(jsonGridData);
		} catch (AxisFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
