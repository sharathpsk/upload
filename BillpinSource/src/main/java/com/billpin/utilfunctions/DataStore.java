package com.billpin.utilfunctions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
public class DataStore {
	@Autowired
	MongoOperations mongo;
	
	@Autowired
	MongoTemplate temp;
	
	
	protected final MongoOperations getMongoOps() {
		return mongo;
	}
	public Object findAndModify(Criteria criteria,Update update,Class requiredClass,String collectionName,boolean returnNew ){
		return mongo.findAndModify(
				new Query(criteria), update, new FindAndModifyOptions().returnNew(returnNew), requiredClass, collectionName
			);
	}
	public Object findOne(Query query,Class requiredClass,String collectionName){
		return mongo.findOne(query,requiredClass,collectionName);
	}
	public Object findAndModify(Query query,Update update,Class requiredClass,String collectionName){
		return mongo.findAndModify(query, update, requiredClass, collectionName);
	}
	
	public void insert(Object obj,String collectionName){
		
		mongo.insert(obj,collectionName);
		
	}
	
public void save(Object obj,String collectionName){
		
		mongo.save(obj,collectionName);
		
	}
	public Object find(Query query,Class requiredClass,String collectionName){
		
		return mongo.find(query, requiredClass,collectionName);
	}
	
	public Long count(Query query,Class requiredClass,String collectionName){
		return mongo.count(query, requiredClass, collectionName);
		
	}
	
	public void remove(Query query,String collectionName){
		mongo.remove(query,collectionName);
	}
}
