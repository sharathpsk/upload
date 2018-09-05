package com.billpin.models;

import java.util.List;

public class Customer {

	private String _id;
	private long custID;
	private String customerEmail;
	private String customerPassword;
	private String customerName;
	private List<String> FriendsList;
	private double totalExpenses;
	
	
	public List<String> getFriendsList() {
		return FriendsList;
	}
	public void setFriendsList(List<String> friendsList) {
		FriendsList = friendsList;
	}
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public double getTotalExpenses() {
		return totalExpenses;
	}
	public void setTotalExpenses(double totalExpenses) {
		this.totalExpenses = totalExpenses;
	}
	public long getCustID() {
		return custID;
	}
	public void setCustID(long custID) {
		this.custID = custID;
	}
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
	public String getCustomerPassword() {
		return customerPassword;
	}
	public void setCustomerPassword(String customerPassword) {
		this.customerPassword = customerPassword;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	
}
