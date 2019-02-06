package com.revature.delegate;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reavature.tjordan.models.Employee;
import com.reavature.tjordan.models.ReimbursementReq;
import com.revature.tjordan.services.ReimbursementReqService;

public class RequestDelegate {
		
	private final ObjectMapper mapper = new ObjectMapper();
	
	
	
	
		public void getAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			List<ReimbursementReq> allReq = ReimbursementReqService.getReimbursementService().getAll();
			resp.setContentType("application/json");
			resp.getWriter().append(mapper.writeValueAsString(allReq));
		}
	
	
		public void getGResolved(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			List<ReimbursementReq> allResolved = ReimbursementReqService.getReimbursementService().getResolvedGlobal();
			resp.setContentType("application/json");
			resp.getWriter().append(mapper.writeValueAsString(allResolved));
		}
		
		
		
		
		
	
	
	public void addRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("In Request Delegate");
		HttpSession session = req.getSession();
		System.out.println(session.getAttribute("currentEmployee"));
		//Get employee ID
		Employee currentEmployee = (Employee)session.getAttribute("currentEmployee");
		int emp_ID = currentEmployee.getEmp_ID();
		//System.out.println(emp_ID);
		//Get Parameters from form
		String request_type = req.getParameter("type");
		double req_amount = Double.parseDouble(req.getParameter("amount"));
		//Get Local Date
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		LocalDate currentDate = LocalDate.now();
		String currentDateStr = dateFormat.format(currentDate);
		//int emp_ID = 201;
		System.out.println("EMPID: " + emp_ID);
		System.out.println("Type: " + request_type);
		System.out.println("Amount: " + req_amount);
		System.out.println("Date: " + currentDateStr);
		//Call addRequest Method
		List<ReimbursementReq> updatedPending = ReimbursementReqService.getReimbursementService().addRequest(emp_ID, currentDateStr, request_type, req_amount);
		//Output JSON Object
//		resp.setContentType("application/json");
//		resp.getWriter().append(mapper.writeValueAsString(updatedPending));
		
		req.getRequestDispatcher("static/EmployeeIndex.jsp").forward(req, resp);
		
		
	}
}
