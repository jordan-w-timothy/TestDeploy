package com.revature.dao;

import java.util.List;

import com.reavature.tjordan.models.ReimbursementReq;

public interface ReimbursementReqDao {
	
	public List<ReimbursementReq> getAllSpecificRequest(int UserID);
	public List<ReimbursementReq> getAllPending(int UserID);
	public List<ReimbursementReq> getAllPendingMan();
	public List<ReimbursementReq> getAllResolved(int UserID);
	public List<ReimbursementReq> getResolvedGlobal();
	public List<ReimbursementReq> addRequest(int emp_ID, String subDate, String type, double amount);
	
	public List<ReimbursementReq> getAll();

}
