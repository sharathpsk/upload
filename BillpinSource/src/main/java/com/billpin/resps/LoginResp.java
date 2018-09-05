package com.billpin.resps;

import com.billpin.models.BaseResponse;

public class LoginResp extends BaseResponse{

	private long userId;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	
	
}
