package com.capgemini.bank.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.capgemini.bank.bean.DemandDraft;
import com.cg.payroll.util.ConnectionProvider;
public class DemandDraftDAO implements IDemandDraftDAO{
		Connection connection=ConnectionProvider.getdBConnection();
		Logger logger=Logger.getLogger(DemandDraftDAO.class);
	@Override
	public int addDemandDraftDetails(DemandDraft demandDraft) {
		try {
			PreparedStatement ps=connection.prepareStatement("INSERT INTO demand_draft(transaction_id,customer_name,in_favor_of,phone_number,date_of_transaction,dd_amount,dd_commission,dd_description) VALUES(accountNo.NEXTVAL,?,?,?,?,?,?,?)");
			ps.setString(1,demandDraft.getCustomerName());
			ps.setString(2,demandDraft.getInFavorOf());
			ps.setLong(3,demandDraft.getPhoneNumber());
			ps.setString(4,"");
			ps.setInt(5, demandDraft.getDemandDraftAmount());
			ps.setInt(6, demandDraft.getCommission());
			ps.setString(7, demandDraft.getRemarks());
			ps.executeUpdate();
			
			PreparedStatement ps1=connection.prepareStatement("SELECT MAX(transaction_id) FROM demand_draft");
			
			ResultSet resultset=ps1.executeQuery();
			
			resultset.next();
			
			int transactionId=resultset.getInt(1);
			return transactionId;
			
		} catch (SQLException e) {
			logger.error(e);
		}
		return 0;
	}
	@Override
	public DemandDraft getDemandDraftDetaiils(int transactionId) {
		return null;
	}
}
