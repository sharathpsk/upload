package com.billpin.resps;

import java.util.List;

import com.billpin.models.BaseResponse;

public class UserFrndsList extends BaseResponse{

	private List<String> FriendsList;

	public List<String> getFriendsList() {
		return FriendsList;
	}

	public void setFriendsList(List<String> friendsList) {
		FriendsList = friendsList;
	}
	
	
}
