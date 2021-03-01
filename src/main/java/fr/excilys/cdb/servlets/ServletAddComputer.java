package fr.excilys.cdb.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.excilys.cdb.dto.AddComputerFormOutput;
import fr.excilys.cdb.dto.mapper.DTOComputerMapper;
import fr.excilys.cdb.dto.validator.ValidatorAddComputer;
import fr.excilys.cdb.exception.CustomSQLException;
import fr.excilys.cdb.exception.EmptyError;
import fr.excilys.cdb.exception.ParseError;
import fr.excilys.cdb.model.Company;
import fr.excilys.cdb.model.Computer;
import fr.excilys.cdb.services.ServiceCompany;
import fr.excilys.cdb.services.ServiceComputer;

//@WebServlet("/Test")
public class ServletAddComputer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ServletAddComputer() {

	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServiceCompany serviceCompany = ServiceCompany.getServiceCompanyInstance();
		List<Company> companies = new ArrayList<Company>();
		try {
			companies = serviceCompany.listCompanies();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} catch (CustomSQLException customSQLException) {
			customSQLException.noDataDetected();
		}
		request.setAttribute("companies", companies);
		this.getServletContext().getRequestDispatcher("/WEB-INF/lib/views/addComputer.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
		String computerName = request.getParameter("computerName"),
				introducedDate = request.getParameter("introducedDate"),
				discontinuedDate = request.getParameter("discontinuedDate"),
				companyId = request.getParameter("companyId");
		AddComputerFormOutput addComputerFormOutput = new AddComputerFormOutput.AddComputerFormOutputBuilder()
				.withComputerName(computerName).withDiscontinuedDate(discontinuedDate)
				.withIntroducedDate(introducedDate).withCompanyId(companyId).build();

		try {
			ValidatorAddComputer.validate(addComputerFormOutput);
			ServiceComputer serviceComputer = ServiceComputer.getServiceComputerInstance();
			Computer computer = DTOComputerMapper.AddComputerFormOutputToComputer(addComputerFormOutput);
			serviceComputer.createComputer(computer);
		} catch (ParseError parseError) {
			parseError.parseErrorDetected();
		} catch (EmptyError emptyError) {
			emptyError.emptyComputerName();
		} catch (CustomSQLException customSQLException) {
			customSQLException.connectionLostDetected();
		}
	}

}
