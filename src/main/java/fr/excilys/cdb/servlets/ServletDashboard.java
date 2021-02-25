package fr.excilys.cdb.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.excilys.cdb.database.Pageable;
import fr.excilys.cdb.model.Computer;
import fr.excilys.cdb.model.Company;
import fr.excilys.cdb.services.ServiceComputer;

//@WebServlet("/Test")
public class ServletDashboard extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ServletDashboard() {
    	
    }

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
    	ServiceComputer serviceComputer = ServiceComputer.getServiceComputerInstance();
    	List<Computer> computers = new ArrayList<Computer>();
    	Pageable pageable = new Pageable();
		try {
			computers = serviceComputer.listComputersPageable(pageable);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
    	request.setAttribute("computers", computers);
    	this.getServletContext().getRequestDispatcher( "/WEB-INF/lib/views/dashboard.jsp" ).forward( request, response );
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
