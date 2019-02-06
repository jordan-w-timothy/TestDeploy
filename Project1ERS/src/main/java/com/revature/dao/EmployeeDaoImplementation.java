package com.revature.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.reavature.tjordan.models.Employee;
import com.revature.tjordan.services.EmployeeService;
import com.revature.tjordan.util.JDBCConnectionUtil;








public class EmployeeDaoImplementation implements EmployeeDao {
	
	private static EmployeeDaoImplementation EmployeeDao;
	final static Logger Employeelog = Logger.getLogger(EmployeeDaoImplementation.class);
	
	private EmployeeDaoImplementation() {
		
	}
	
	
	public static EmployeeDaoImplementation getEmployeeDao() {
		if(EmployeeDao == null) {
			EmployeeDao = new EmployeeDaoImplementation();
		}
		
		return EmployeeDao;
	}
	
	
	public Employee validateLogin(String userName, String password) {
		Employee tempEmployee = null;
		Employeelog.info("Inside validateLogin method");
		//Get employee info from employee table and store in employee object.
		try (Connection conn = JDBCConnectionUtil.getConnection()){
			
			String sql = "select * from employeetable where emp_username = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, userName);
			ResultSet results = ps.executeQuery();
			while(results.next()) {
				tempEmployee = new Employee(
						results.getInt("emp_ID"),
						results.getString("emp_firstname"),
						results.getString("emp_lastname"),
						results.getString("emp_username"),
						results.getString("emp_password"),
						results.getString("emp_email"),
						results.getString("is_manager"));
						
			}
			
			if(tempEmployee != null) {
				//existing username
				//if not null check password
				
				if(password.equals(tempEmployee.getEmp_password())) {
					//if password does match successful login return employee object
					return tempEmployee;
				} else {
					// if password doesnt match invalid login
					return null;
				}
			} else {
				//invalid username
				return null;
			}
			
					
			
		} catch (SQLException e) {
			Employeelog.info("SQL Exception from validate login.");
			Employeelog.error(e.getMessage());
			return null;
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
	
	
	
	
	
	public Employee getEmployee(String userName) {
	System.out.println("Inside getEmployee");
	System.out.println("username" + userName);
		try(Connection conn = JDBCConnectionUtil.getConnection()){
			//bad practice- never hard code values in a method
			//how to avoid - using prepared statement - ps supports pparameterized sql
			String sql = "select * from employeetable where emp_username = '" + userName + "'";
			Statement stmt = conn.createStatement();
			ResultSet results = stmt.executeQuery(sql);
			while(results.next()) {
				//log.info("inside the while loop for getUser");
				return new Employee(
						results.getInt("emp_ID"),
						results.getString("emp_firstname"),
						results.getString("emp_lastname"),
						results.getString("emp_username"),
						results.getString("emp_password"),
						results.getString("emp_email"),
						results.getString("is_manager")
						
						);
			} 
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
		
		
		return null;
		
		
		
		
		
		
		
		
	}
	
	public boolean checkUser(String userName, String password) {
		
		String inputPassword = password;
		Employee tempEmployee = getEmployee(userName);
		if(tempEmployee != null) {
			Employeelog.info("Valid login");
			if(tempEmployee.getEmp_password().equals(inputPassword)) {
				return true;
			} else {
				System.out.println("Invalid Password");
				return false;
			}
		} else {
			System.out.println("Not an employee!");
			return false;
		}
		
		
	}
	
	
	public Employee updateEmployee(int emp_ID, String firstName, String userName, String lastName, String email, String password)  {
		System.out.println(firstName);
		System.out.println(lastName);
		System.out.println(email);
		System.out.println(password);
		try(Connection conn = JDBCConnectionUtil.getConnection()){
			String sql = "UPDATE EMPLOYEETABLE SET EMP_FIRSTNAME = ?, EMP_LASTNAME = ?, EMP_PASSWORD = ?, EMP_EMAIL = ? WHERE EMP_ID = ?";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, firstName);
			ps.setString(2, lastName);
			ps.setString(3, password);
			ps.setString(4, email);
			ps.setInt(5, emp_ID);
			
			if(ps.executeUpdate() >= 0) {
				//Get new employee based off of ID
				Employee updatedEmp = EmployeeService.getEmployeeService().getEmployee(userName);
				return updatedEmp;
			} else {
				
				throw new SQLException();
			}
			
		} catch(SQLException e) {
			Employeelog.error("SQL Exception in updateEmployee in EmployeeDaoImpl");
			Employeelog.error(e.getMessage());
			Employeelog.error(e.getStackTrace());		}
		
		
		
		return null;
	}
	
	public List<Employee> getAllEmployees() {
		try(Connection conn = JDBCConnectionUtil.getConnection()) {
			Statement stmt = conn.createStatement();
			String sql = "select * from EMPLOYEETABLE";
			
			ResultSet results = stmt.executeQuery(sql);
			
			List<Employee> allEmployees = new ArrayList<>();
			while(results.next()) {
				allEmployees.add(new Employee(
						results.getInt("EMP_ID"),
						results.getString("EMP_FIRSTNAME"),
						results.getString("EMP_LASTNAME"),
						results.getString("EMP_USERNAME"),
						results.getString("EMP_EMAIL")
						
						
						
						
						
						));
			}
			
			return allEmployees;
		} catch(SQLException e) {
			Employeelog.error("SQLException in getAllEmployees");
			Employeelog.error(e.getMessage());
			Employeelog.error(e.getStackTrace());
		}
		
		
		
		
		
		return new ArrayList<>();
	}
	
	
	public boolean addEmployee(String userName, String firstName, String lastName, String password, String email, String isManager) {
		try(Connection conn = JDBCConnectionUtil.getConnection()) {
			String sql = "Call ADD_EMPLOYEE(?,?,?,?,?,?)";
			CallableStatement callst = conn.prepareCall(sql);
			callst.setString(1, firstName);
			callst.setString(2, lastName);
			callst.setString(3, userName);
			callst.setString(4,  password);
			callst.setString(5, email);
			callst.setString(6, isManager);
			
			if(callst.executeUpdate() == 0) {
				return true;
			} else {
				
				return false;
			}
		}catch(SQLException e) {
			Employeelog.error("SQLException in addEmployee in EmployeeDaoImplementation");
			Employeelog.error(e.getMessage());
			Employeelog.error(e.getStackTrace());
		}
		
		return false;
	}

}
