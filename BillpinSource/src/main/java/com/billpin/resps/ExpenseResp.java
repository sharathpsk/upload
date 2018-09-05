package com.billpin.resps;


public class ExpenseResp {
	
	private long userid;
	private long frndID;
	private String friendName;
	private double totalAmount;
	private double youOwe;
	
	
	
	public long getUserid() {
		return userid;
	}
	public void setUserid(long userid) {
		this.userid = userid;
	}
	public long getFrndID() {
		return frndID;
	}
	public void setFrndID(long frndID) {
		this.frndID = frndID;
	}
	public String getFriendName() {
		return friendName;
	}
	public void setFriendName(String friendName) {
		this.friendName = friendName;
	}
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public double getYouOwe() {
		return youOwe;
	}
	public void setYouOwe(double youOwe) {
		this.youOwe = youOwe;
	}
	
	
	
}
