package com.revature.tjordan.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.reavature.tjordan.models.Employee;
import com.revature.delegate.EmployeeDelegate;

import com.revature.delegate.LoginDelegate;
import com.revature.delegate.ManagerDelegate;
import com.revature.delegate.RequestDelegate;


public class Dispatcher {
	private static ManagerDelegate md = new ManagerDelegate();
	private static EmployeeDelegate ed = new EmployeeDelegate();
	private static LoginDelegate ld = new LoginDelegate();
	private static RequestDelegate rd = new RequestDelegate();
	
	
	
	public void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		Employee currentEmployee = (Employee)session.getAttribute("currentEmployee");
		String switchString = req.getRequestURI().substring(req.getContextPath().length()+1);
		while(switchString.indexOf("/")>0) {
			switchString = switchString.substring(0, switchString.indexOf("/"));
		}
		System.out.println(switchString);
		
		switch(switchString) {
		case "AllRequest":
			rd.getAll(req,resp);
			break;
		case "getGResolved":
			rd.getGResolved(req, resp);
			break;
		case "getAllPending":
			md.getAllPending(req, resp);
			break;
		case "addEmployee":
			md.addEmployee(req, resp);
			break;
		case "allUsers":
			ed.getAllEmployees(req, resp);
			break;
		case "updateProfile":
				ed.updateProfile(req, resp);
				break;
		case "addRequest":
			//System.out.println("In Add Req");
			//System.out.println(session.getAttribute("currentEmployee"));
			rd.addRequest(req, resp);
			break;
		case "getResolved":
			ed.getAllResolved(req, resp);
			break;
		case "getPending":
			ed.getAllPending(req, resp);
			break;
		case "info":
			//ed.info(req,resp);
			//return ed.processAVC(req, resp);
			break;
		case "returnHome":
			if(currentEmployee.getIsManager().equals("Y")) {
				md.goHome(req, resp);
			} else {
				ed.goHome(req, resp);
			}
			break;
		case"editProfile":
			if(currentEmployee.getIsManager().equals("Y")) {
				md.editProfile(req,resp);
			} else {
				ed.editProfile(req,resp);
			}
			
			break;
		case "profileView":
			if(currentEmployee.getIsManager().equals("Y")) {
				md.viewProfile(req,resp);
			} else {
				ed.viewProfile(req,resp);
			}
			
			break;
		case "manHome":
			System.out.println("inside manHome case");
			md.goHome(req, resp);
			break;
			//return null;
		case "empHome":
			System.out.println("inside employeeHome case");
			ed.goHome(req, resp);
			break;
			//return null;
		case "login":
			if("POST".equals(req.getMethod())) {
				System.out.println("inside ifPOST login case");
			ld.login(req, resp);
		} else {
			System.out.println("in login else");
			ld.getPage(req, resp);
		} //return null;
			break;
		case "logout":
			ld.logout(req, resp);
			break;
			//return null;
		default:
			break;
			//return null;
		}
		//return null;
	}
}
