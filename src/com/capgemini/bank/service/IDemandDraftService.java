package com.capgemini.bank.service;
import com.capgemini.bank.bean.DemandDraft;
import com.capgemini.bank.exception.InvalidAmountException;
public interface IDemandDraftService {
	int addDemandDraftDetails(DemandDraft demandDraft) throws InvalidAmountException;
	DemandDraft getDemandDraftDetails(int transactionId);
}
