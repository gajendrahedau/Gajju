package com.cg.payroll.daoservices;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.cg.payroll.beans.Associate;
import com.cg.payroll.beans.BankDetails;
import com.cg.payroll.beans.Salary;
import com.cg.payroll.util.ConnectionProvider;
public class AssociateDAOImpl implements AssociateDAO{
	private Connection conn=ConnectionProvider.getdBConnection();
	@Override
	public Associate save(Associate associate) throws SQLException {
			try {
				conn.setAutoCommit(false);
				PreparedStatement pstmt1=conn.prepareStatement("INSERT INTO Associate (associateID,yearlyInvestmentUnder80C,firstName,lastName,department,designation,pancard,emailId) VALUES(SEQUENCE_ID.NEXTVAL,?,?,?,?,?,?,?)");
				pstmt1.setInt(1,associate.getYearlyInvestmentUnder80C()); 
				pstmt1.setString(2,associate.getFirstName());
				pstmt1.setString(3,associate.getLastName());
				pstmt1.setString(4,associate.getDepartment());
				pstmt1.setString(5,associate.getDesignation());
				pstmt1.setString(6,associate.getPancard());
				pstmt1.setString(7,associate.getEmailId());
				pstmt1.executeUpdate();
				
				PreparedStatement pstmt2=conn.prepareStatement("SELECT MAX(associateId) FROM Associate");
				
				ResultSet rs=pstmt2.executeQuery();
				rs.next();
				int associateId=rs.getInt(1);
				
				PreparedStatement pstmt3=conn.prepareStatement("INSERT INTO BankDetails(accountnumber,bankname,ifsccode,associateid) VALUES(?,?,?,?)");
				pstmt3.setLong(1, associate.getBankdetails().getAccountNumber());
				pstmt3.setString(2, associate.getBankdetails().getBankName());
				pstmt3.setString(3, associate.getBankdetails().getIfscCode());
				pstmt3.setInt(4, associateId);
				pstmt3.executeUpdate();
				
				PreparedStatement pstmt4=conn.prepareStatement("INSERT INTO Salary(associateId,basicSalary,epf,companyPf) VALUES(?,?,?,?)");
				pstmt4.setInt(1,associateId);
				pstmt4.setInt(2,associate.getSalary().getBasicSalary());
				pstmt4.setInt(3, associate.getSalary().getEpf());
				pstmt4.setInt(4, associate.getSalary().getCompanyPf());
				pstmt4.executeUpdate();
				conn.commit();
				associate.setAssociateID(associateId);
				return associate;
				
			} catch (SQLException e) {
				e.printStackTrace();
				conn.rollback();
				throw e;
			}finally{
				conn.setAutoCommit(true);
			}
	}
	@Override
	public Associate findOne(int associateId) throws SQLException{
		PreparedStatement pstmt1=conn.prepareStatement("SELECT * FROM Associate WHERE associateId="+associateId);
		ResultSet associateRS=pstmt1.executeQuery();
		if(associateRS.next()){
			String firstName=associateRS.getString("firstName");
			String lastName=associateRS.getString("lastName");
			String department=associateRS.getString("department");
			String designation=associateRS.getString("designation");
			String pancard=associateRS.getString("pancard");
			String emailId=associateRS.getString("emailId");
			int yearlyInvestmentUnder80C=associateRS.getInt("yearlyInvestmentUnder80C");
			Associate associate=new Associate(associateId, yearlyInvestmentUnder80C, firstName, lastName, department, designation, pancard, emailId, null, null);
			
			PreparedStatement pstmt2=conn.prepareStatement("SELECT * FROM BankDetails WHERE associateId="+associateId);
			ResultSet bankDetailsRS=pstmt2.executeQuery();
			bankDetailsRS.next();
			int accountNumber=bankDetailsRS.getInt("accountNumber");
			String bankName=bankDetailsRS.getString("bankName");
			String ifscCode=bankDetailsRS.getString("ifscCode");
			associate.setBankdetails(new BankDetails(accountNumber, bankName, ifscCode));
			
			PreparedStatement pstmt3=conn.prepareStatement("SELECT * FROM Salary WHERE associateId="+associateId);
			
			ResultSet salaryRs =pstmt3.executeQuery();
			salaryRs.next();
			associate.setSalary(new Salary(salaryRs.getInt("basicSalary"), salaryRs.getInt("epf"), salaryRs.getInt("companyPf")));
			
			return associate;
		}
		
		return null;
	}
	@Override
	public ArrayList<Associate> findAll() throws SQLException {
		ArrayList<Associate> associates=new ArrayList<>();
		PreparedStatement pstmt1=conn.prepareStatement("SELECT * FROM Associate");
		ResultSet associateRS=pstmt1.executeQuery();
		while(associateRS.next()){
			int associateId=associateRS.getInt("associateId");
			String firstName=associateRS.getString("firstName");
			String lastName=associateRS.getString("lastName");
			String department=associateRS.getString("department");
			String designation=associateRS.getString("designation");
			String pancard=associateRS.getString("pancard");
			String emailId=associateRS.getString("emailId");
			int yearlyInvestmentUnder80C=associateRS.getInt("yearlyInvestmentUnder80C");
			Associate associate=new Associate(associateId, yearlyInvestmentUnder80C, firstName, lastName, department, designation, pancard, emailId, null, null);
			
			PreparedStatement pstmt2=conn.prepareStatement("SELECT * FROM BankDetails");
			ResultSet bankDetailsRS=pstmt2.executeQuery();
			bankDetailsRS.next();
			int accountNumber=bankDetailsRS.getInt("accountNumber");
			String bankName=bankDetailsRS.getString("bankName");
			String ifscCode=bankDetailsRS.getString("ifscCode");
			associate.setBankdetails(new BankDetails(accountNumber, bankName, ifscCode));
			
			PreparedStatement pstmt3=conn.prepareStatement("SELECT * FROM Salary");
			
			ResultSet salaryRs =pstmt3.executeQuery();
			salaryRs.next();
			associate.setSalary(new Salary(salaryRs.getInt("basicSalary"), salaryRs.getInt("epf"), salaryRs.getInt("companyPf")));
			
			associates.add(associate);
			return associates;
		}
		
		return null;
	}
	@Override
	public void  update(Associate associate) throws SQLException {
		PreparedStatement pstmt=conn.prepareStatement("INSERT INTO Salary(hra ,conveyenceAllowance ,otherAllowance ,personalAllowance ,monthlyTax ,gratuity ,grossSalary ,netSalary,epf,companyPf) VALUES(?,?,?,?,?,?,?,?,?,?) WHERE associateId="+associate.getAssociateID());
		pstmt.setInt(1,associate.getSalary().getHra());
		pstmt.setInt(2, associate.getSalary().getConveyenceAllowance());
		pstmt.setInt(3, associate.getSalary().getOtherAllowance());
		pstmt.setInt(4, associate.getSalary().getPersonalAllowance());
		pstmt.setInt(5, associate.getSalary().getMonthlyTax());
		pstmt.setInt(6, associate.getSalary().getGratuity());
		pstmt.setInt(7, associate.getSalary().getGrossSalary());
		pstmt.setInt(8, associate.getSalary().getNetSalary());
		pstmt.setInt(9, associate.getSalary().getEpf());
		pstmt.setInt(10, associate.getSalary().getCompanyPf());
		pstmt.executeUpdate();
	}
	/*private static int ASSOCIATE_COUNTER=101;
	private static HashMap<Integer, Associate> associates=new HashMap<>();
	@Override
	public Associate save(Associate associate) {
		associate.setAssociateID(ASSOCIATE_COUNTER++);
		associates.put(associate.getAssociateID(), associate);
		return associate;
	}
	@Override
	public Associate findOne(int associateId) {
		return associates.get(associateId);
	}
	@Override
	public ArrayList<Associate> findAll() {
		return new ArrayList<>(associates.values());
	}*/
}
