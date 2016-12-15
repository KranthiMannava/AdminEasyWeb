package com.cyient.designAnalysis;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.axis2.databinding.types.NormalizedString;
import org.apache.log4j.Logger;

import com.cyient.admineasy.admineasyservices.AdminEasyServicesStub;
import com.cyient.admineasy.admineasyservices.AdminEasyServicesStub.CheckedProject;
import com.cyient.admineasy.admineasyservices.AdminEasyServicesStub.DeleteDesignsRequest;
import com.cyient.admineasy.admineasyservices.AdminEasyServicesStub.DeleteDesignsRequestE;
import com.cyient.admineasy.admineasyservices.AdminEasyServicesStub.DeleteDesignsResponse;
import com.cyient.admineasy.admineasyservices.AdminEasyServicesStub.DeleteDesignsResponseE;
import com.cyient.admineasy.admineasyservices.AdminEasyServicesStub.SelectedDesignIDs;

public class AdminDesignDeleteSelectedDesignServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static final Logger LOGGER = Logger.getLogger(AdminDesignDeleteSelectedDesignServlet.class);
	/**
	 * Constructor of the object.
	 */
	public AdminDesignDeleteSelectedDesignServlet() {
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
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");

		String selectedDesign = request.getParameter("param1");
		String ischeckedProject = request.getParameter("param2");
		
		LOGGER.info("Calling Stub ");
		AdminEasyServicesStub adminEasyServicesStub = new AdminEasyServicesStub("http://172.16.18.75:8080/gss/web/services/AdminEasyServices/");
		DeleteDesignsRequestE deleteDesignsRequestE=new DeleteDesignsRequestE();
		DeleteDesignsRequest deleteDesignsRequest=new DeleteDesignsRequest();
		
		CheckedProject checkedProject=new CheckedProject();
		checkedProject.setCheckedProject(new NormalizedString(ischeckedProject));
		SelectedDesignIDs selectedDesignIDs=new SelectedDesignIDs();
		selectedDesignIDs.setSelectedDesignIDs(new NormalizedString(selectedDesign));
		deleteDesignsRequest.setCheckedProject(checkedProject);
		deleteDesignsRequest.setSelectedDesignIDs(selectedDesignIDs);
		deleteDesignsRequestE.setDeleteDesignsRequest(deleteDesignsRequest);
		
		DeleteDesignsResponseE deleteDesignsResponseE=new DeleteDesignsResponseE();
		deleteDesignsResponseE=adminEasyServicesStub.deleteDesignsService(deleteDesignsRequestE);
		LOGGER.info("getService Response is "+deleteDesignsResponseE);
		System.out.println(deleteDesignsResponseE);
		response.getWriter().println(deleteDesignsResponseE);
		
		
		
		
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
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
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
