package com.capgemini.bank.service;
import com.capgemini.bank.bean.DemandDraft;
import com.capgemini.bank.dao.DemandDraftDAO;
import com.capgemini.bank.dao.IDemandDraftDAO;
import com.capgemini.bank.exception.InvalidAmountException;
public class DemandDraftService implements IDemandDraftService{
	IDemandDraftDAO dao=new DemandDraftDAO();
	@Override
	public int addDemandDraftDetails(DemandDraft demandDraft) throws InvalidAmountException{
		int amount=demandDraft.getDemandDraftAmount();
		int commission=0;
			try {
				if(amount<=0)
				throw new InvalidAmountException("Amount must not be negative or zero.");
				if(amount<=5000)
					commission=10;
				else if(amount>5000 && amount<=10000)
					commission=41;
				else if(amount>10000 && amount<=100000)
					commission=51;
				else if(amount>100000 && amount<=500000)
					commission=306;
				demandDraft.setCommission(commission);
				return dao.addDemandDraftDetails(demandDraft);
			}
		catch (InvalidAmountException e) {
			e.printStackTrace();
		}
			return commission;
	}
	@Override
	public DemandDraft getDemandDraftDetails(int transactionId) {
		
		return null;
	}
}
