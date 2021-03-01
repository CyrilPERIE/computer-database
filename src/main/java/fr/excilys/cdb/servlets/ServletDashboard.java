package fr.excilys.cdb.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.excilys.cdb.database.Pageable;
import fr.excilys.cdb.model.Computer;
import fr.excilys.cdb.services.ServiceComputer;

//@WebServlet("/Test")
public class ServletDashboard extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Pageable pageable;
	ServiceComputer serviceComputer = ServiceComputer.getServiceComputerInstance();
	private int totalNumberOfComputer;

	public ServletDashboard() {
		this.pageable = new Pageable(totalNumberOfComputer);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		totalNumberOfComputer = serviceComputer.totalNumberComputer();
		pageable.setMax(totalNumberOfComputer);
		checkAnyEvent(request);

		List<Integer> indexPage = pageable.sendPageIndexes();
		request.setAttribute("indexPage", indexPage);

		List<Computer> computers = sendComputers();
		request.setAttribute("computers", computers);
		
		int currentPage = 1;
		request.setAttribute("currentPage", currentPage);

		String totalNumberComputer = String.valueOf(totalNumberOfComputer);
		request.setAttribute("numberOfComputers", totalNumberComputer);
		this.getServletContext().getRequestDispatcher("/WEB-INF/lib/views/dashboard.jsp").forward(request, response);
	}



	private void checkAnyEvent(HttpServletRequest request) {
		if (request.getParameter("go") != null) {
			if (request.getParameter("go").toString().equals("next")) {
				pageable.next();
			} else if (request.getParameter("go").toString().equals("previous")) {
				pageable.previous();
			}
		} else if (request.getParameter("limit") != null) {
			if (request.getParameter("limit").toString().equals("10")) {
				pageable.setLimit(10);
			} else if (request.getParameter("limit").toString().equals("50")) {
				pageable.setLimit(50);
			} else {
				pageable.setLimit(100);
			}
		} else if (request.getParameter("page") != null) {
			int pageIndex = Integer.valueOf(request.getParameter("page"));
			pageable.setOffset(pageIndex*pageable.getLimit());
		}
	}

	private List<Computer> sendComputers() {
		List<Computer> computers = new ArrayList<Computer>();
		try {
			computers = serviceComputer.listComputersPageable(pageable);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return computers;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
