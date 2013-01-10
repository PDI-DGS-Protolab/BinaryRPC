package com.paradigmatecnologico.binaryrpc.avro;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutionException;

import org.apache.avro.ipc.NettyServer;
import org.apache.avro.ipc.NettyTransceiver;
import org.apache.avro.ipc.specific.SpecificRequestor;
import org.apache.avro.ipc.specific.SpecificResponder;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.paradigmatecnologico.binaryrpc.avro.service.AvroTestService.Callback;

public class AvroTest {

	
	private static NettyServer server;

	public static Callback proxy;
	private static NettyTransceiver client;
	
	public static Integer count = 0;
	public static Long time = new Long(0);
	
	
	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
		
		
		System.out.println("**************************");
		System.out.println("     Apache Avro Test     ");
		System.out.println("**************************");
		System.out.println();
		System.out.println("Starting server on port 1984 ...");
		
		//We need tho clean Mongo DB if we want to make same result in every RPC
		// connect to mongoDB, ip and port number
		Mongo mongo=null;
		try {
			mongo = new Mongo("localhost", 27017);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		// get database from MongoDB,
		DB db = mongo.getDB("test");
		try {
			DBCollection coll = db.getCollection("testCollection");
			coll.drop();
		}
		finally{
			mongo.close();
		}
		
		//Define new test server
	    server = new NettyServer(new SpecificResponder(Callback.class,new AvroTestServer()),new InetSocketAddress(1984)); 
	    server.start();
	    //Build simple test client
	    client = new NettyTransceiver(new InetSocketAddress(server.getPort()));
	       
	    proxy = SpecificRequestor.getClient(Callback.class, client);
	    

	    System.out.println("Push 1 to lauch a new call");
	    System.out.println("Push 2 to lauch a batch test (1000 simultaneous calls)");
	    System.out.println("Push 3 to show times and reset counters");
	    System.out.println("Push any other key to exit");
	    
	    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	    Boolean looping= true;
	    Integer index = 1;
	    String input="";
	    while (looping){
	    	input = br.readLine();

	    	switch (input) {
			case "1":
				Thread thread1 = new Thread(new AvroClientRunnable(),"client"+index);
	    	    thread1.start();
	    	    index++;
				break;
			case "2":
				index=1;
    			for (int i = 0; i < 1000; i++) {
					Thread thread2 = new Thread(new AvroClientRunnable(),"client"+i);
					thread2.start();
				}
				break;
			case "3":
				if (count>0){
			    	System.out.println("The average time for "+count +" calls was: "+time/count);	    	
			    }
				count=0;
				time=new Long(0); 
				break;

			default:
				looping=false;
				break;
			}
	    }

	    if (count>0){
	    	System.out.println("The average time for "+count +" calls was: "+time/count);	    	
	    }
	    System.out.println("...Exitting, bye");
	    client.close();
		server.close();
	 }
}
