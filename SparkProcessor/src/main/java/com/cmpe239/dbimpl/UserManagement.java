package com.cmpe239.dbimpl;
import com.cmpe239.beans.Users;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;



public class UserManagement {
	
	public Users getUserMobileNumber(String username)
	{
		MongoClient client = new MongoClient(new ServerAddress("ds061158.mongolab.com",61158));
		DB database = client.getDB("openstack");
        database.authenticate("cmpe239","cmpe239".toCharArray());
        DBCollection collection = database.getCollection("users");
        BasicDBObject query = new BasicDBObject("_id",username);
        DBCursor cursor = collection.find(query);
        String phoneNumber=null;
       	DBObject obj=cursor.next();
       	Users user=new Users();
       	user.setUserName(username);
       	user.setPhoneNumber((String.valueOf(obj.get("phoneNumber"))));
    
        return user;

	}

}
