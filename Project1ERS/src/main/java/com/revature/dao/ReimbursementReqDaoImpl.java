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

import com.reavature.tjordan.models.ReimbursementReq;
import com.revature.tjordan.services.ReimbursementReqService;
import com.revature.tjordan.util.JDBCConnectionUtil;

public class ReimbursementReqDaoImpl {

	private static ReimbursementReqDaoImpl ReimbursementDao;
	final static Logger Reimburselog = Logger.getLogger(ReimbursementReqDaoImpl.class);

	private ReimbursementReqDaoImpl() {

	}

	public static ReimbursementReqDaoImpl getReimbursementDao() {
		if (ReimbursementDao == null) {
			ReimbursementDao = new ReimbursementReqDaoImpl();
		}

		return ReimbursementDao;
	}
	
	
	
	
	
	public List<ReimbursementReq> addRequest(int emp_ID, String subDate, String type, double amount) {
		try(Connection conn = JDBCConnectionUtil.getConnection()) {
			String sql = "Call ADD_REQUEST(?,?,?,?)";
			CallableStatement callst = conn.prepareCall(sql);
			
			callst.setInt(1, emp_ID);
			callst.setString(2, subDate);
			callst.setString(3, type);
			callst.setDouble(4, amount);
			
			if(callst.executeUpdate() == 0) {
				//create a request object and return
				
				List<ReimbursementReq> updated = ReimbursementReqService.getReimbursementService().getAllPending(emp_ID);
				
				for(ReimbursementReq x : updated) {
					System.out.println(x);
				}
				return updated;
				
			} else {
				throw new SQLException();
			}
			
			
			
		} catch(SQLException e) {
			Reimburselog.error("SQL Exception in addRequest Method");
			Reimburselog.error(e.getMessage());
			Reimburselog.error(e.getStackTrace());
		}
		
		
		return new ArrayList<ReimbursementReq>();
	}
	
	
	
	
	
	
	
	public List<ReimbursementReq> getAllSpecificRequests(int emp_ID){
		
		try (Connection conn = JDBCConnectionUtil.getConnection()) {
			String sql = "select * from req_table where EMP_ID = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, emp_ID);
			
			ResultSet results = ps.executeQuery();
			

			List<ReimbursementReq> pendingRequest = new ArrayList<>();
			while (results.next()) {
				pendingRequest.add(new ReimbursementReq(
						results.getInt("EMP_ID"),
						results.getInt("REQ_ID"),
						results.getString("REQ_SUB_DATE"),
						results.getString("REQ_TYPE"),
						results.getDouble("REQ_AMOUNT"),
						results.getString("REQ_STATUS"),
						results.getInt("REQ_MAN_ID"),
						results.getString("REQ_RESOLVED_DATE")
						
						));

			}
			return pendingRequest;
		} catch (SQLException e) {

		}

		return new ArrayList<ReimbursementReq>();
		
		
		
	}
	
	
	
	public List<ReimbursementReq> getResolvedGlobal() {
		try (Connection conn = JDBCConnectionUtil.getConnection()) {
			String sql = "select * from req_table where REQ_Status != ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, "pending");
			
			ResultSet results = ps.executeQuery();
			

			List<ReimbursementReq> resolvedRequest = new ArrayList<>();
			while (results.next()) {
				resolvedRequest.add(new ReimbursementReq(
						results.getInt("EMP_ID"),
						results.getInt("REQ_ID"),
						results.getString("REQ_SUB_DATE"),
						results.getString("REQ_TYPE"),
						results.getDouble("REQ_AMOUNT"),
						results.getString("REQ_STATUS"),
						results.getInt("REQ_MAN_ID"),
						results.getString("REQ_RESOLVED_DATE")
						
						));

			}
			return resolvedRequest;
		} catch (SQLException e) {

		}

		return new ArrayList<ReimbursementReq>();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	public List<ReimbursementReq> getAllResolved(int emp_ID) {
		
		try (Connection conn = JDBCConnectionUtil.getConnection()) {
			String sql = "select * from req_table where EMP_ID = ? AND REQ_STATUS != ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, emp_ID);
			ps.setString(2, "pending");
			ResultSet results = ps.executeQuery();
			

			List<ReimbursementReq> pendingRequest = new ArrayList<>();
			while (results.next()) {
				pendingRequest.add(new ReimbursementReq(
						results.getInt("EMP_ID"),
						results.getInt("REQ_ID"),
						results.getString("REQ_SUB_DATE"),
						results.getString("REQ_TYPE"),
						results.getDouble("REQ_AMOUNT"),
						results.getString("REQ_STATUS"),
						results.getInt("REQ_MAN_ID"),
						results.getString("REQ_RESOLVED_DATE")
						
						));

			}
			return pendingRequest;
		} catch (SQLException e) {

		}

		return new ArrayList<ReimbursementReq>();
	}

		public List<ReimbursementReq> getAll() {
			try (Connection conn = JDBCConnectionUtil.getConnection()) {
				String sql = "select * from req_table";
				PreparedStatement ps = conn.prepareStatement(sql);
				
				
				ResultSet results = ps.executeQuery();
				

				List<ReimbursementReq> pendingRequest = new ArrayList<>();
				while (results.next()) {
					pendingRequest.add(new ReimbursementReq(
							results.getInt("EMP_ID"),
							results.getInt("REQ_ID"),
							results.getString("REQ_SUB_DATE"),
							results.getString("REQ_TYPE"),
							results.getDouble("REQ_AMOUNT"),
							results.getString("REQ_STATUS"),
							results.getInt("REQ_MAN_ID"),
							results.getString("REQ_RESOLVED_DATE")
							
							));

				}
				return pendingRequest;
			} catch (SQLException e) {

			}

			return new ArrayList<ReimbursementReq>();
		}
	
	public List<ReimbursementReq> getAllPendingMan() {
		try (Connection conn = JDBCConnectionUtil.getConnection()) {
			String sql = "select * from req_table where REQ_STATUS = ? ";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, "pending");
			
			ResultSet results = ps.executeQuery();
			

			List<ReimbursementReq> pendingRequest = new ArrayList<>();
			while (results.next()) {
				pendingRequest.add(new ReimbursementReq(
						results.getInt("EMP_ID"),
						results.getInt("REQ_ID"),
						results.getString("REQ_SUB_DATE"),
						results.getString("REQ_TYPE"),
						results.getDouble("REQ_AMOUNT"),
						results.getString("REQ_STATUS"),
						results.getInt("REQ_MAN_ID"),
						results.getString("REQ_RESOLVED_DATE")
						
						));

			}
			return pendingRequest;
		} catch (SQLException e) {

		}

		return new ArrayList<ReimbursementReq>();
	}
	
		
	

	public List<ReimbursementReq> getAllPending(int emp_ID) {

		try (Connection conn = JDBCConnectionUtil.getConnection()) {
			String sql = "select * from req_table where EMP_ID = ? AND REQ_STATUS = ? ";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, emp_ID);
			ps.setString(2, "pending");
			ResultSet results = ps.executeQuery();
			

			List<ReimbursementReq> pendingRequest = new ArrayList<>();
			while (results.next()) {
				pendingRequest.add(new ReimbursementReq(
						results.getInt("EMP_ID"),
						results.getInt("REQ_ID"),
						results.getString("REQ_SUB_DATE"),
						results.getString("REQ_TYPE"),
						results.getDouble("REQ_AMOUNT"),
						results.getString("REQ_STATUS"),
						results.getInt("REQ_MAN_ID"),
						results.getString("REQ_RESOLVED_DATE")
						
						));

			}
			return pendingRequest;
		} catch (SQLException e) {

		}

		return new ArrayList<ReimbursementReq>();
	}

}
