package com.billpin.resps;

import java.util.List;

import com.billpin.models.BaseResponse;

public class ExpenseList extends BaseResponse{

	
	private List<ExpenseResp> txns_list;

	public List<ExpenseResp> getTxns_list() {
		return txns_list;
	}

	public void setTxns_list(List<ExpenseResp> txns_list) {
		this.txns_list = txns_list;
	}
	
	
	
}
