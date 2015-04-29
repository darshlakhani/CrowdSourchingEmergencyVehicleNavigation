package com.twilio.message;

import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.cmpe239.beans.Users;
import com.cmpe239.dbimpl.UserManagement;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;


public class TwilioMessage {
	
	public static final String ACCOUNT_SID = "AC4cb10d4afc95bd06309900a4857361c7";
	public static final String AUTH_TOKEN = "d566bb75c5fde3b6a7cca5ace5d35985";

	
	public void sendMessage(String username) throws TwilioRestException
	{
		TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
		 
	    // Build a filter for the MessageList
	    List<NameValuePair> params = new ArrayList<NameValuePair>();
	    String message="Hello Fellow driver, please make a way so that the emergency vehicle can reach its destination";
	    String body=message;
	    if(message!=null && message.length()==0){
	    	body="Default Message Here";
	    }
	    UserManagement um=new UserManagement();
	    Users user=um.getUserMobileNumber(username);
	    params.add(new BasicNameValuePair("Body",body));
	    params.add(new BasicNameValuePair("To",user.getPhoneNumber()));
	    params.add(new BasicNameValuePair("From", "+12015747858"));
	    //params.add(new BasicNameValuePair("MediaUrl", "http://www.example.com/hearts.png"));
	     
	     
	    MessageFactory messageFactory = client.getAccount().getMessageFactory();
	    Message message1 = messageFactory.create(params);
	    //System.out.println(message.getSid());

	}

}
