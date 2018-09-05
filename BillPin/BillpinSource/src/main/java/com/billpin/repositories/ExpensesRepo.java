package com.billpin.repositories;


import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.billpin.models.Expenses;

public interface ExpensesRepo extends MongoRepository<Expenses, String>{

	List<Expenses> findAllByFriendsID(long friendsID);
	List<Expenses> findAllByUser(long user);
	
	
}
