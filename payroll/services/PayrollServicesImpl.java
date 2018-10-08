package com.cg.payroll.services;
import java.sql.SQLException;
import java.util.ArrayList;

import com.cg.payroll.beans.Associate;
import com.cg.payroll.beans.BankDetails;
import com.cg.payroll.beans.Salary;
import com.cg.payroll.daoservices.AssociateDAO;
import com.cg.payroll.daoservices.AssociateDAOImpl;
import com.cg.payroll.exceptions.AssociateDetailsNotFoundException;
import com.cg.payroll.exceptions.PayrollServicesDownException;
public class PayrollServicesImpl implements PayrollServices{
	private AssociateDAO associateDAO=new AssociateDAOImpl();	
	@Override
	public int acceptAssociateDetails(int yearlyInvestmentUnder80C,
			String firstName, String lastName, String department,
			String designation, String pancard, String emailId,
			int basicSalary, int epf, int companyPf, long accountNumber,
			String bankName, String ifscCode)
			throws PayrollServicesDownException {
			try {
				Associate associate=new Associate(yearlyInvestmentUnder80C, firstName, lastName, department, designation, pancard, emailId, new BankDetails(accountNumber, bankName, ifscCode),new Salary(basicSalary, epf, companyPf));
				associate=associateDAO.save(associate);
				return associate.getAssociateID();
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Servi5465ces are Down. Please try after some time");
			}
			return 0;
	}
	@Override
	public int calculateNetSalary(int associateId)
			throws AssociateDetailsNotFoundException,
			PayrollServicesDownException {
			Associate associate=getAssociateDetails(associateId);
			if(associate==null) throw new AssociateDetailsNotFoundException();
			associate.getSalary().setHra((30*associate.getSalary().getBasicSalary())/100);
			associate.getSalary().setOtherAllowance((20*associate.getSalary().getBasicSalary())/100);
			associate.getSalary().setPersonalAllowance((25*associate.getSalary().getBasicSalary())/100);
			associate.getSalary().setConveyenceAllowance((15*associate.getSalary().getBasicSalary())/100);
			associate.getSalary().setGrossSalary((associate.getSalary().getBasicSalary())+(associate.getSalary().getHra())+(associate.getSalary().getConveyenceAllowance())+(associate.getSalary().getPersonalAllowance())+(associate.getSalary().getOtherAllowance()));	
			int salaryPerAnnum=(associate.getSalary().getGrossSalary()*12);		
			int yearlyInvestment=associate.getYearlyInvestmentUnder80C();
			int companyPf=associate.getSalary().getCompanyPf()*12;
			int epf=associate.getSalary().getEpf()*12;
			int totalInvestmentPerAnnum=yearlyInvestment+companyPf+epf;
			if(totalInvestmentPerAnnum>150000)
				totalInvestmentPerAnnum=150000;
			if(salaryPerAnnum<=250000)
				associate.getSalary().setMonthlyTax(0);
			else if(salaryPerAnnum>250000 && salaryPerAnnum<=500000)
				associate.getSalary().setMonthlyTax((5*((salaryPerAnnum-totalInvestmentPerAnnum)-250000))/(100*12));
				else if(salaryPerAnnum>500000 && salaryPerAnnum<=1000000){
					int till5=12500;
					int till10=(20*((salaryPerAnnum-totalInvestmentPerAnnum)-500000))/100;
					associate.getSalary().setMonthlyTax((till5+till10)/12);
				}
				else if(salaryPerAnnum>1000000){
						int till10=112500;
						int greater10=(30*((salaryPerAnnum-totalInvestmentPerAnnum)-1000000))/100;
						associate.getSalary().setMonthlyTax((till10+greater10)/12);
					}	
			associate.getSalary().setNetSalary((associate.getSalary().getGrossSalary())-(associate.getSalary().getMonthlyTax()));
			/*try {
				associateDAO.update(associate);
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Services are down");
			}*/
			return associate.getSalary().getNetSalary();
	}
	@Override
	public Associate getAssociateDetails(int associateId)
			throws PayrollServicesDownException,
			AssociateDetailsNotFoundException {
		Associate associate=null;
		try {
			associate = associateDAO.findOne(associateId);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Services are Down. Please try after some time.");
		}
		if(associate==null) throw new AssociateDetailsNotFoundException("Associate Details not found");
		return associate;
	}
	@Override
	public ArrayList<Associate> getAllAssociateDetails()
			throws PayrollServicesDownException {
		try {
			return associateDAO.findAll();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Services are down. Please try after some time.");
		}
		return null;
	}
}
