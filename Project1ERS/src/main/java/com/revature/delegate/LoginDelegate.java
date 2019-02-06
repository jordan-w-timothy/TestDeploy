package com.revature.delegate;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.reavature.tjordan.models.Employee;
import com.revature.dao.EmployeeDaoImplementation;
import com.revature.tjordan.services.EmployeeService;



public class LoginDelegate {
		
	final static Logger loginLogger = Logger.getLogger(LoginDelegate.class);
	public void login(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		String username = req.getParameter("user");
		String password = req.getParameter("pass");
		
		System.out.println("inside login delegate");
		
		
		Employee currentEmployee = EmployeeService.getEmployeeService().validateLogin(username, password);
		//Check for invalid login.
		if(currentEmployee == null) {
			//If currentEmployee is null then invalid login.
			System.out.println("BAD LOGIN");
			resp.sendRedirect("login");
		} else {
			//If not null then valid login.
			//Store Employee object as session variable.
			HttpSession session = req.getSession();
			session.setAttribute("currentEmployee", currentEmployee);
			//Determine if the employee object is a manager.
			
			
			
			
			if(currentEmployee.getIsManager().equals("Y")) {
				System.out.println("in here");
				//If manager then redirect to dispatcher with "manHome".
				resp.sendRedirect("manHome");
			} else {
				//If not manager then redirect to dispatcher with "empHome".
				resp.sendRedirect("empHome");
			}
		}
		
		
		
		
		
		
//		boolean isValid = EmployeeService.getEmployeeService().checkUser(username, password);
//		if(!isValid) {
//			resp.sendRedirect("login");
//		} else {
//			Employee dbEmp = EmployeeService.getEmployeeService().getEmployee(username);
//			Employee currentEmp = new Employee(dbEmp.getEmp_ID(), dbEmp.getEmp_firstName(), dbEmp.getEmp_lastName(), dbEmp.getEmp_userName(), dbEmp.getEmp_email(), dbEmp.getIsManager());
//			//System.out.println(currentEmp.getIsManager());
//			if(currentEmp.getIsManager().equals("Y")) {
//				HttpSession session = req.getSession();
//				session.setAttribute("currentEmployee", currentEmp);
//				
//				System.out.println("getting rdy to send to managerHome");
//				resp.sendRedirect("manHome");
//			} else {
//				HttpSession session = req.getSession();
//				session.setAttribute("currentEmployee", currentEmp);
//				System.out.println("getting rdy to send to employeeHome");
//				resp.sendRedirect("empHome");
//			}
//			
//		}
		
//		if(login == null) {
//			resp.sendRedirect("login");
//		} else {
//			HttpSession session = req.getSession();
//			session.setAttribute("user", login);
//			resp.sendRedirect("home");
//		}
	}
	
	public void getPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("In log in delegate getPage");
		HttpSession session = req.getSession();
		if(session.getAttribute("currentEmployee")==null) {
			System.out.println("in logindelegate getPage if");
			req.getRequestDispatcher("index.html").forward(req, resp);
		} else {
			System.out.println("Inside of loging delegate getpage else");
			resp.sendRedirect("login");
			req.getSession().invalidate();
			
		}
//		HttpSession session = req.getSession();
//		if(session.getAttribute("user")==null) {
//			req.getRequestDispatcher("static/login.html").forward(req,resp);
//		} else {
//			resp.sendRedirect("home");
//		}
	}
	
	public void logout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		req.getSession().invalidate();
		resp.sendRedirect("login");
//	}
	
}
}
