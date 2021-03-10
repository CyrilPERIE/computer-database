package fr.excilys.cdb.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import fr.excilys.cdb.database.Pageable;
import fr.excilys.cdb.exception.CustomSQLException;
import fr.excilys.cdb.model.Computer;
import fr.excilys.cdb.services.ServiceComputer;

//@WebServlet("/ServletDashboard")
public class ServletDashboard extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Autowired
	ServiceComputer serviceComputer;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
		super.init(config);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		Pageable pageable = (Pageable) session.getAttribute("pageable");
		if (pageable == null) {
			pageable = new Pageable();
			session.setAttribute("pageable", pageable);
		}
		
		checkAnyEvent(request, session);
		
		List<Computer> computers = sendComputers(request, session);
		request.setAttribute("computers", computers);		

		String totalNumberComputer = String.valueOf(pageable.getMaxComputer());
		request.setAttribute("numberOfComputers", totalNumberComputer);
		
		List<Integer> indexPage = pageable.sendPageIndexes();
		request.setAttribute("indexPage", indexPage);
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/lib/views/dashboard.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		if (request.getParameter("selection") != null) {
			List<String> deleteIdList;
			try {
				deleteIdList = Arrays.asList(request.getParameter("selection").split(","));
				for (String id : deleteIdList) {
					serviceComputer.deleteComputer(Integer.parseInt(id));
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (CustomSQLException e) {
				e.printStackTrace();
			}
		}
		
		doGet(request, response);
	}
	
	private int totalNumberComputer(HttpSession session) {
		Pageable pageable = (Pageable) session.getAttribute("pageable");
		int totalNumberOfComputer = 0;
		try {
			totalNumberOfComputer = serviceComputer.totalNumberComputer(pageable);
		} catch (ClassNotFoundException e) {
			System.out.println("Can't connect to database");
		} catch (CustomSQLException customSQLException) {
			customSQLException.noDataDetected();
		}
		return totalNumberOfComputer;
	}

	private void checkAnyEvent(HttpServletRequest request, HttpSession session) {
		Pageable pageable = (Pageable) session.getAttribute("pageable");
		if (request.getParameter("go") != null) {
			handleGo(request, session);
		} else if (request.getParameter("limit") != null) {
			handleLimit(request, session);
		} else if (request.getParameter("page") != null) {
			int pageIndex = Integer.valueOf(request.getParameter("page"));
			pageable.setOffsetParameter(pageIndex*pageable.getLimitParameter());
		}
		else if (request.getParameter("orderBy") != null) {
			pageable.setOrderBy(request.getParameter("orderBy"));
		}
		session.setAttribute("pageable", pageable);
	}

	private void handleGo(HttpServletRequest request, HttpSession session) {
		Pageable pageable = (Pageable) session.getAttribute("pageable");
		if (("next").equals(request.getParameter("go"))) {
			pageable.next();
		} else if (("previous").equals(request.getParameter("go"))) {
			pageable.previous();
		}
		session.setAttribute("pageable", pageable);
	}

	private void handleLimit(HttpServletRequest request, HttpSession session) {
		Pageable pageable = (Pageable) session.getAttribute("pageable");
		if (request.getParameter("limit").equals("10")) {
			pageable.setLimitParameter(10);
		} else if (request.getParameter("limit").equals("50")) {
			pageable.setLimitParameter(50);
		} else {
			pageable.setLimitParameter(100);
		}
		session.setAttribute("pageable", pageable);
	}

	private List<Computer> sendComputers(HttpServletRequest request, HttpSession session) {
		Pageable pageable = (Pageable) session.getAttribute("pageable");
		String search = request.getParameter("search");
		List<Computer> computers = new ArrayList<Computer>();
		
		try {
			if(search != null) {
				pageable.setSearch(search);
			}
			
			computers = serviceComputer.selectComputersLikePageableOrderBy(pageable);
		}catch (ClassNotFoundException e) {
			System.out.println("Can't connect to database");
		}catch (CustomSQLException customSQLException) {
			customSQLException.noDataDetected();
		}
	
		int totalNumberOfComputer = totalNumberComputer(session);
		pageable.setMaxComputer(totalNumberOfComputer);
		
		session.setAttribute("pageable", pageable);
		return computers;
	}

}
