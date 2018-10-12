package com.capgemini.bank.bean;

public class DemandDraft {
	private String customerName;
	private long phoneNumber;
	private String inFavorOf;
	private int demandDraftAmount;
	private String remarks;
	private int commission;
	public int getCommission() {
		return commission;
	}
	public void setCommission(int commission) {
		this.commission = commission;
	}
	public DemandDraft(String customerName, long phoneNumber, String inFavorOf,
			int demandDraftAmount, String remarks, int commission) {
		super();
		this.customerName = customerName;
		this.phoneNumber = phoneNumber;
		this.inFavorOf = inFavorOf;
		this.demandDraftAmount = demandDraftAmount;
		this.remarks = remarks;
		this.commission = commission;
	}
	public DemandDraft(String customerName, long phoneNumber, String inFavorOf,
			int demandDraftAmount, String remarks) {
		super();
		this.customerName = customerName;
		this.phoneNumber = phoneNumber;
		this.inFavorOf = inFavorOf;
		this.demandDraftAmount = demandDraftAmount;
		this.remarks = remarks;
	}
	public DemandDraft() {}
	@Override
	public String toString() {
		return "DemandDraft [customerName=" + customerName + ", phoneNumber="
				+ phoneNumber + ", inFavorOf=" + inFavorOf
				+ ", demandDraftAmount=" + demandDraftAmount + ", remarks="
				+ remarks + "]";
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public long getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getInFavorOf() {
		return inFavorOf;
	}
	public void setInFavorOf(String inFavorOf) {
		this.inFavorOf = inFavorOf;
	}
	public int getDemandDraftAmount() {
		return demandDraftAmount;
	}
	public void setDemandDraftAmount(int demandDraftAmount) {
		this.demandDraftAmount = demandDraftAmount;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
