package fr.excilys.cdb.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.excilys.cdb.database.Pageable;
import fr.excilys.cdb.exception.CustomSQLException;
import fr.excilys.cdb.model.Computer;
import fr.excilys.cdb.services.ServiceComputer;

//@WebServlet("/Test")
public class ServletDashboard extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Pageable pageable;
	ServiceComputer serviceComputer = ServiceComputer.getInstance();
	private int totalNumberOfComputer;

	public ServletDashboard() {
		this.pageable = new Pageable(totalNumberOfComputer);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			totalNumberOfComputer = serviceComputer.totalNumberComputer();
		} catch (ClassNotFoundException e) {
			System.out.println("Can't connect to database");
		} catch (CustomSQLException customSQLException) {
			customSQLException.noDataDetected();
		}
		pageable.setMax(totalNumberOfComputer);
		checkAnyEvent(request);

		List<Integer> indexPage = pageable.sendPageIndexes();
		request.setAttribute("indexPage", indexPage);

		List<Computer> computers = sendComputers();
		request.setAttribute("computers", computers);

		String totalNumberComputer = String.valueOf(totalNumberOfComputer);
		request.setAttribute("numberOfComputers", totalNumberComputer);
		this.getServletContext().getRequestDispatcher("/WEB-INF/lib/views/dashboard.jsp").forward(request, response);
	}



	private void checkAnyEvent(HttpServletRequest request) {
		if (request.getParameter("go") != null) {
			handleGo(request);
		} else if (request.getParameter("limit") != null) {
			handleLimit(request);
		} else if (request.getParameter("page") != null) {
			int pageIndex = Integer.valueOf(request.getParameter("page"));
			pageable.setOffset(pageIndex*pageable.getLimit());
		}
	}

	private void handleGo(HttpServletRequest request) {
		if (request.getParameter("go").toString().equals("next")) {
			pageable.next();
		} else if (request.getParameter("go").toString().equals("previous")) {
			pageable.previous();
		}
	}

	private void handleLimit(HttpServletRequest request) {
		if (request.getParameter("limit").toString().equals("10")) {
			pageable.setLimit(10);
		} else if (request.getParameter("limit").toString().equals("50")) {
			pageable.setLimit(50);
		} else {
			pageable.setLimit(100);
		}
	}

	private List<Computer> sendComputers() {
		List<Computer> computers = new ArrayList<Computer>();
		try {
			computers = serviceComputer.listComputersPageable(pageable);
		}catch (ClassNotFoundException e) {
			System.out.println("Can't connect to database");
		}catch (CustomSQLException customSQLException) {
			customSQLException.noDataDetected();
		}
		return computers;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
