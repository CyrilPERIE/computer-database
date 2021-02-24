package fr.excilys.cdb.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.excilys.cdb.database.Pageable;
import fr.excilys.cdb.model.Company;
import fr.excilys.cdb.services.ServiceCompany;

@WebServlet("/Test")
public class ServletDashboard extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ServletDashboard() {
    	
    }

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
    	ServiceCompany serviceCompany = ServiceCompany.getServiceCompanyInstance();
    	List<Company> computers = new ArrayList<Company>();
    	Pageable pageable = new Pageable();
		try {
			computers = serviceCompany.listComputersPageable(pageable);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
    	request.setAttribute("computers", computers);
    	this.getServletContext().getRequestDispatcher( "/WEB-INF/lib/views/dashboard.jsp" ).forward( request, response );
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
