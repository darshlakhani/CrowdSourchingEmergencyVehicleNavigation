package com.cloudera.sparkwordcount;

import java.nio.ByteBuffer;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.RegionUtils;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.kinesis.AmazonKinesisClient;
import com.amazonaws.services.kinesis.model.CreateStreamRequest;
import com.amazonaws.services.kinesis.model.PutRecordRequest;
import com.amazonaws.services.kinesis.model.PutRecordResult;

public class KinesisClient {
	
	static String accessKey="";
	static String secretKey="";
	
	public static void main(String args[]) throws InterruptedException{
		
		AWSCredentials credentials= new BasicAWSCredentials(accessKey, secretKey);
		String input="";
		AmazonKinesisClient kinesisClient = new AmazonKinesisClient(credentials);
		kinesisClient.setRegion(Region.getRegion(Regions.US_WEST_2));
	    //kinesisClient.setEndpoint("https://ec2.eu-west-1.amazonaws.com");
	    kinesisClient.setEndpoint("https://kinesis.us-west-2.amazonaws.com");
	    double sLat=37.337086;
	    double sLong=-121.882639;
	    double dLat=37.353335;
	    double dLong=-121.894288;
	    for (int j = 0; j < 200; j++) 
		{
		  PutRecordRequest putRecordRequest = new PutRecordRequest();
		  putRecordRequest.setStreamName("cmpeharish");
		  if(j%50==0)
		  {
			  input=""+((sLat)-(Math.random()/50)-(Math.random()/10))+","+((sLong)-(Math.random()/50)-(Math.random()/10))+",5105567131,ambulance";
		  }
		  else{
			  input=""+((sLat)-(Math.random()/50)-(Math.random()/10))+","+((sLat)-(Math.random()/50)-(Math.random()/10))+",5105567131,vehicle";
		  }
		  System.out.println(input);
		  putRecordRequest.setData(ByteBuffer.wrap( input.getBytes() ));
		  putRecordRequest.setPartitionKey( String.format( "partitionKey-%d", j%5 ));  
		  PutRecordResult putRecordResult = kinesisClient.putRecord(putRecordRequest);
		  System.out.println(putRecordResult.getShardId());
		  
		}
	    for (int j = 0; j < 200; j++) 
		{
		  PutRecordRequest putRecordRequest = new PutRecordRequest();
		  putRecordRequest.setStreamName("cmpeharish");
		  if(j%50==0)
		  {
			  input=""+((dLat)-(Math.random()/50)-(Math.random()/10))+","+((dLat)-(Math.random()/50)-(Math.random()/10))+",5105567131,ambulance";
		  }
		  else{
			  input=""+((dLat)-(Math.random()/50)-(Math.random()/10))+","+((dLat)-(Math.random()/50)-(Math.random()/10))+",5105567131,vehicle";
		  }
		  System.out.println(input);
		  
		  putRecordRequest.setData(ByteBuffer.wrap( input.getBytes() ));
		  putRecordRequest.setPartitionKey( String.format( "partitionKey-%d", j%5 ));  
		  PutRecordResult putRecordResult = kinesisClient.putRecord(putRecordRequest);
		  System.out.println(putRecordResult.getShardId());
		  
		}

		
	}
	

}
