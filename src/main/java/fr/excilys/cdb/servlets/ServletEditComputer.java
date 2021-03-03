package fr.excilys.cdb.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.excilys.cdb.dto.EditComputerFormInput;
import fr.excilys.cdb.dto.mapper.ComputerDTOMapper;
import fr.excilys.cdb.dto.mapper.DTOComputerMapper;
import fr.excilys.cdb.dto.validator.ValidatorEditComputer;
import fr.excilys.cdb.exception.CustomDateException;
import fr.excilys.cdb.exception.CustomSQLException;
import fr.excilys.cdb.exception.EmptyError;
import fr.excilys.cdb.exception.ParseError;
import fr.excilys.cdb.model.Company;
import fr.excilys.cdb.model.Computer;
import fr.excilys.cdb.services.ServiceCompany;
import fr.excilys.cdb.services.ServiceComputer;


//@WebServlet("/ServletEditComputer")
public class ServletEditComputer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Map<String, String> errors = new HashMap<>();
       
    public ServletEditComputer() {
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("id") != null) {
			ServiceComputer serviceComputer = ServiceComputer.getInstance();
			try {
				Computer computer = serviceComputer.showComputerDetails(Integer.valueOf(request.getParameter("id"))).get(0);
				EditComputerFormInput editComputerFormInput = ComputerDTOMapper.computerToAddComputerFormOutput(computer);
				request.setAttribute("editComputerFormInput", editComputerFormInput);
			} catch (NumberFormatException | ClassNotFoundException | SQLException | CustomSQLException e) {
				e.printStackTrace();
			}
		}
		
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
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/lib/views/editComputer.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String computerName = request.getParameter("computerName"),
				introducedDate = request.getParameter("introducedDate"),
				discontinuedDate = request.getParameter("discontinuedDate"),
				companyId = request.getParameter("companyId"),
				computerId = request.getParameter("computerId");
		System.out.println(computerId);
		
		EditComputerFormInput editComputerFormInput = new EditComputerFormInput.EditComputerFormInputBuilder()
				.withComputerName(computerName).withDiscontinuedDate(discontinuedDate)
				.withIntroducedDate(introducedDate).withCompanyId(companyId).withComputerId(computerId).build();
		
		System.out.println("editComputerFormInput : "  + editComputerFormInput);
		validateUserEntries(editComputerFormInput);
		doGet(request, response);
	}

	private void validateUserEntries(EditComputerFormInput editComputerFormInput) {
		try {
			ValidatorEditComputer.validate(editComputerFormInput);
			ServiceComputer serviceComputer = ServiceComputer.getInstance();
			Computer computer = DTOComputerMapper.editComputerFormOutputToComputer(editComputerFormInput);
			serviceComputer.updateComputer(computer, computer.getId());
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
