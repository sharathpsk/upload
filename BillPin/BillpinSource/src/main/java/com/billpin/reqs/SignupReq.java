package com.billpin.reqs;


public class SignupReq {
	private String customerEmail;
	private String customerPassword;
	private String customerName;
	private String customerPassword2;
	
	
	
	public String getCustomerPassword2() {
		return customerPassword2;
	}
	public void setCustomerPassword2(String customerPassword2) {
		this.customerPassword2 = customerPassword2;
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
