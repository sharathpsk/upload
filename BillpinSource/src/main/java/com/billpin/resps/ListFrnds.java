package com.billpin.resps;

import java.util.List;

import com.billpin.models.BaseResponse;

public class ListFrnds extends BaseResponse{

	private List<Findfrnds> friends;

	public List<Findfrnds> getFriends() {
		return friends;
	}

	public void setFriends(List<Findfrnds> friends) {
		this.friends = friends;
	}
	
	
}
