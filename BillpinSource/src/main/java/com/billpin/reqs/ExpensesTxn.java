package com.billpin.reqs;



import java.util.List;



public class ExpensesTxn {

	private String userid;
	private String amount;
	private List<String> frndsIDs;
	
	
	public List<String> getFrndsIDs() {
		return frndsIDs;
	}
	public void setFrndsIDs(List<String> frndsIDs) {
		this.frndsIDs = frndsIDs;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	
	
	
	
	
}
