package com.billpin.models;



public class Expenses {

	private String _id;
	private long txnID;

	private long user;
	private double userAmount;
	private long friendsID;
	private String friendsName;
	private double individualAmount;
	private String paid;
	private long date;
	
	
	public String getFriendsName() {
		return friendsName;
	}
	public void setFriendsName(String friendsName) {
		this.friendsName = friendsName;
	}
	public long getDate() {
		return date;
	}
	public void setDate(long date) {
		this.date = date;
	}
	public String getPaid() {
		return paid;
	}
	public void setPaid(String paid) {
		this.paid = paid;
	}
	public long getTxnID() {
		return txnID;
	}
	public void setTxnID(long txnID) {
		this.txnID = txnID;
	}
	public double getIndividualAmount() {
		return individualAmount;
	}
	public void setIndividualAmount(double individualAmount) {
		this.individualAmount = individualAmount;
	}
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	
	public long getUser() {
		return user;
	}
	public void setUser(long user) {
		this.user = user;
	}
		public double getUserAmount() {
		return userAmount;
	}
	public void setUserAmount(double userAmount) {
		this.userAmount = userAmount;
	}
		public long getFriendsID() {
		return friendsID;
	}
	public void setFriendsID(long friendsID) {
		this.friendsID = friendsID;
	}
	
	
	
	
}
