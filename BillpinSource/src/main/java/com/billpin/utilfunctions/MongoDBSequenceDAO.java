package com.billpin.utilfunctions;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

@Component
public class MongoDBSequenceDAO extends DataStore{

	/**
	 * Name of the collection where sequences will be  stored.
	 */
	public static final String COLLECTION_NAME = "sequence";
	
	/**
	 * Method to get the next sequence number for a particular collection. This method
	 * will increment the current sequence number value for a particular collection and return the
	 * {@link MongoDBSequence} object.
	 * 
	 * @param collectionName
	 * @return
	 */
	public long getNextSequece(String collectionName){
		
		Update update 	= new Update();
		update.inc("seq", 1);
		MongoDBSequence seq;
		
		 seq = (MongoDBSequence)findOne(new Query(Criteria.where("id").is(collectionName)), MongoDBSequence.class,COLLECTION_NAME);
		
		 if(seq==null){
			 seq=new MongoDBSequence();
			seq.setId(collectionName);
			seq.setSeq(1000);
			insert(seq,COLLECTION_NAME);
		}else{
			seq = (MongoDBSequence) findAndModify(Criteria.where("id").is(collectionName), update
					, 	MongoDBSequence.class, COLLECTION_NAME, true);

		}
		
		return seq.getSeq();
	}
}

