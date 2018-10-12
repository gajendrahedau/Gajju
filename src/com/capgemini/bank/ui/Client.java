package com.capgemini.bank.ui;

import java.util.Scanner;

import com.capgemini.bank.bean.DemandDraft;
import com.capgemini.bank.exception.InvalidAmountException;
import com.capgemini.bank.service.DemandDraftService;
import com.capgemini.bank.service.IDemandDraftService;

public class Client {
	public static void main(String args[]){
		IDemandDraftService services=new DemandDraftService();
		Scanner scanner=new Scanner(System.in);
		System.out.println("Enter the name of the customer: ");
		String customerName=scanner.next();
		System.out.println("Enter customer phone number: ");
		 long phoneNumber=scanner.nextLong();
		 System.out.println("In favour of: ");
		 String inFavorOf=scanner.next();
		 System.out.println("Enter Demand Draft amount(in Rs): ");
		 int demandDraftAmount=scanner.nextInt();
		 System.out.println("Enter Remarks: ");
		 String remarks=scanner.next();
		 System.out.println(customerName);
		 System.out.println(phoneNumber);
		 System.out.println( inFavorOf);
		 System.out.println(demandDraftAmount);
		 System.out.println(remarks);
		DemandDraft demandDraft=new DemandDraft(customerName, phoneNumber, inFavorOf, demandDraftAmount, remarks);
		int transactionId=0;
		try {
			transactionId = services.addDemandDraftDetails(demandDraft);
		} catch (InvalidAmountException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		System.out.println("Your Demand Draft request has been successfully registered along with "+transactionId);
	}
}
