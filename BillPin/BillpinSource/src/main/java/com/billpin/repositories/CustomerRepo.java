package com.billpin.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.billpin.models.Customer;
import java.lang.String;
import java.util.List;



public interface CustomerRepo extends MongoRepository<Customer, String>{

	Customer findByCustomerName(String customername);
	Customer findByCustomerEmail(String customeremail);
	Customer findByCustomerEmailAndCustomerPassword(String email ,String password);
	Customer findByCustID(long custID);
	List<Customer> findAll();
	
	
}
