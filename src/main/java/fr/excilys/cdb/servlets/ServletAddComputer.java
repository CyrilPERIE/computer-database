package fr.excilys.cdb.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.excilys.cdb.dto.AddComputerFormOutput;
import fr.excilys.cdb.dto.mapper.DTOComputerMapper;
import fr.excilys.cdb.dto.validator.ValidatorAddComputer;
import fr.excilys.cdb.exception.CustomDateException;
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
	Map<String, String> errors = new HashMap<>();

	public ServletAddComputer() {

	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServiceCompany serviceCompany = ServiceCompany.getServiceCompanyInstance();
		List<Company> companies = new ArrayList<Company>();
		try {
			companies = serviceCompany.listCompanies();
		} catch (CustomSQLException customSQLException) {
			errors.put("global", customSQLException.noDataDetected());
		} catch (ClassNotFoundException e) {
			errors.put("global", "can't connect to database");
		}
		
		request.setAttribute("companies", companies);
		
		Map<String, String> errorsForRequest = new HashMap<>(errors);
		errors.clear();
		request.setAttribute("errorsForRequest", errorsForRequest);
		this.getServletContext().getRequestDispatcher("/WEB-INF/lib/views/addComputer.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String computerName = request.getParameter("computerName"),
				introducedDate = request.getParameter("introducedDate"),
				discontinuedDate = request.getParameter("discontinuedDate"),
				companyId = request.getParameter("companyId");
		AddComputerFormOutput addComputerFormOutput = new AddComputerFormOutput.AddComputerFormOutputBuilder()
				.withComputerName(computerName).withDiscontinuedDate(discontinuedDate)
				.withIntroducedDate(introducedDate).withCompanyId(companyId).build();

		validateUserEntries(addComputerFormOutput);
		doGet(request, response);
	}

	private void validateUserEntries(AddComputerFormOutput addComputerFormOutput) {
		try {
			ValidatorAddComputer.validate(addComputerFormOutput);
			ServiceComputer serviceComputer = ServiceComputer.getInstance();
			Computer computer = DTOComputerMapper.AddComputerFormOutputToComputer(addComputerFormOutput);
			serviceComputer.createComputer(computer);
		} catch (ParseError parseError) {
			errors.put("dateField",parseError.parseErrorDetected());
		} catch (EmptyError emptyError) {
			errors.put("computerNameField",emptyError.emptyComputerName());
		} catch (CustomSQLException customSQLException) {
			customSQLException.connectionLostDetected();
		} catch (ClassNotFoundException e) {
			errors.put("global", "can't connect to database");
		} catch (CustomDateException customDateException) {
			errors.put("dateField", customDateException.notAfter());
		}
	}

}
