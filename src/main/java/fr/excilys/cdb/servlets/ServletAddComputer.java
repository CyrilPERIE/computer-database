package fr.excilys.cdb.servlets;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.excilys.cdb.controller.Utilitaire;
import fr.excilys.cdb.model.Company;
import fr.excilys.cdb.services.ServiceCompany;
import fr.excilys.cdb.services.ServiceComputer;

//@WebServlet("/Test")
public class ServletAddComputer extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ServletAddComputer() {
    	
    }

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
    	ServiceCompany serviceCompany = ServiceCompany.getServiceCompanyInstance();
    	List<Company> companies = new ArrayList<Company>();
		try {
			companies = serviceCompany.listCompanies();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
    	request.setAttribute("companies", companies);
    	this.getServletContext().getRequestDispatcher( "/WEB-INF/lib/views/addComputer.jsp" ).forward( request, response );
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
		String computerName = request.getParameter("computerName");
		Date indtroducedDate = null, discontinuedDate = null;
		try {
			indtroducedDate = Utilitaire.stringToDate(request.getParameter("introducedDate"));
			discontinuedDate = Utilitaire.stringToDate(request.getParameter("discontinuedDate"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int companyId = Integer.valueOf(request.getParameter("companyName"));
		
		ServiceComputer serviceComputer = ServiceComputer.getServiceComputerInstance();
		
		serviceComputer.createComputer(companyId, computerName, indtroducedDate, discontinuedDate);
		
	}

}
