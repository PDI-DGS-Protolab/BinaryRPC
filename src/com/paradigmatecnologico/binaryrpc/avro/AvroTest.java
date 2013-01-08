package com.paradigmatecnologico.binaryrpc.avro;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import org.apache.avro.AvroRemoteException;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.ipc.CallFuture;
import org.apache.avro.ipc.NettyServer;
import org.apache.avro.ipc.NettyTransceiver;
import org.apache.avro.ipc.specific.SpecificRequestor;
import org.apache.avro.ipc.specific.SpecificResponder;
import org.apache.avro.specific.SpecificDatumWriter;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.paradigmatecnologico.binaryrpc.avro.service.AvroTestService.Callback;
import com.paradigmatecnologico.binaryrpc.avro.service.RequestData;
import com.paradigmatecnologico.binaryrpc.avro.service.Response;
import com.paradigmatecnologico.binaryrpc.avro.service.Search;
import com.paradigmatecnologico.binaryrpc.avro.service.State;

public class AvroTest {

	
	private static NettyServer server;
	private static DB db;
	private static Callback proxy;
	private static NettyTransceiver client;
	
	public static Integer count = 0;
	public static Long time = new Long(0);
	
	   
	  /**
	   * 
	   * @author Paradigma
	   * 
	   * This Class implements simple Avro server which manage asynchronous calls. Can manage creation or search calls
	   *
	   */
	  public static class AvroTestServer implements Callback {
		  
		@Override
		public Response create(RequestData request) throws AvroRemoteException {
			
			Response response= new Response();
			
			// connect to mongoDB, ip and port number
			Mongo mongo=null;
			try {
				mongo = new Mongo("localhost", 27017);
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
			}
			
			// get database from MongoDB,
			// if database doesn't exists, mongoDB will create it automatically
			db = mongo.getDB("test");
			
			
			try {
				//Slow the execution for test asynchronous calls, if neccesary
				//Thread.sleep((long) (new Random().nextDouble()*10000));
				
				DBCollection coll = db.getCollection("testCollection");
				
				//is necesary convert all string that request parse as Utf8 objects to string in order to save the response.
				List<String> addresses =new ArrayList<String>();
				for (CharSequence string : request.getAddresses()) {
					addresses.add(string.toString());
				}
				BasicDBObject doc = new BasicDBObject("ID", request.getID()).
						append("name", request.getName().toString()).
						append("state", request.getState().toString()).
						append("addresses", addresses).
						//append("telephones", request.getTelephones()).
						append("member", request.getMember()).
						append("description", request.getDescription().toString());
				
				coll.insert(doc);
				
				//Format the confirmation message
				response.setResponseData(request);
				response.setCode(200);
				ObjectId id = (ObjectId)doc.get( "_id" );
				response.setMessage("created with Database id = "+id.toString());
				
				return response;
			} catch (Exception e) {
				
				response.setCode(404);
				response.setMessage(e.getMessage());
				return response;
			}
		}

		@Override
		public Response search(Search request) throws AvroRemoteException {
			return null;
		}

		@Override
		public void create(RequestData request,	org.apache.avro.ipc.Callback<Response> callback) {
			Response response= new Response();
			
			// connect to mongoDB, ip and port number
			Mongo mongo=null;
			try {
				mongo = new Mongo("localhost", 27017);
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
			}
			
			// get database from MongoDB,
			// if database doesn't exists, mongoDB will create it automatically
			db = mongo.getDB("test");
			
			
			try {
				//Slow the thread execution for test asynchronous calls
				//Thread.sleep((long) (new Random().nextDouble()*10000));
				
				DBCollection coll = db.getCollection("testCollection");
				
				//is necessary convert all string that request parse as Utf8 objects to string in order to save the response.
				List<String> addresses =new ArrayList<String>();
				for (CharSequence string : request.getAddresses()) {
					addresses.add(string.toString());
				}
				Map<String, Long> telephones = new HashMap<String,Long>(5);
				Iterator<Entry<String, Long>> it = telephones.entrySet().iterator(); 
				while (it.hasNext()) {
					Entry<String, Long> entry = (Entry<String, Long>) it.next();
					telephones.put(entry.getKey().toString(), entry.getValue());
				}
				BasicDBObject doc = new BasicDBObject("ID", request.getID()).
						append("name", request.getName().toString()).
						append("state", request.getState().toString()).
						append("addresses", addresses).
						append("telephones", telephones).
						append("member", request.getMember()).
						append("description", request.getDescription().toString());
				
				coll.insert(doc);
				
				//Format the confirmation message
				response.setResponseData(request);
				response.setCode(200);
				ObjectId id = (ObjectId)doc.get( "_id" );
				response.setMessage("created with Database id = "+id.toString());
				
				callback.handleResult(response);
			} catch (Exception e) {
				
				response.setCode(404);
				response.setMessage(e.getMessage());
				callback.handleResult(response);
			}finally{
				mongo.close();
			}
		}

		@Override
		public void search(Search request,
				org.apache.avro.ipc.Callback<Response> callback)
				throws IOException {
			
		}
		
	  }
	  
	  
	/**
	 * 
	 * @author Paradigma
	 * 
	 * This Class is a sample Runnable for make simple creation calls to the server
	 *
	 */
	public static class AvroClientRunnable implements Runnable{

		@Override
		public void run() {
			//Populate RequestData
		    RequestData req = new RequestData(); 
		    
		    List<CharSequence> addresses =new ArrayList<CharSequence>();
		    addresses.add("Calle 1");
		    addresses.add("Calle 2");
		    //Add String Array
		    req.setAddresses(addresses);
		    //Add 500 bytes string
		    req.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam et ipsum felis, sit amet tincidunt erat. Duis id mauris sit amet eros consectetur " +
	    					"posuere condimentum nec ipsum. Fusce nec est eu mauris fringilla facilisis. Cras a mauris feugiat eros lacinia molestie in non arcu. Maecenas " +
	    					"in felis eu augue ornare pharetra eget a mauris. Mauris mi metus, fringilla eu lobortis sed, convallis non turpis. Aenean mi justo, dapibus eget " +
	    					"accumsan quis, laoreet nec libero. Mauris ac turpis id elit cras amet.");
		    //Add double value
		    req.setID(new Random().nextDouble());
		    //Add Boolean value
		    req.setMember(true);
		    //Add String
		    req.setName("name01");
		    //Add enum type
		    req.setState(State.SINGLE);
		    
		    Map<CharSequence, Long> telephones = new HashMap<CharSequence,Long>(5);
		    telephones.put("home", new Long(1234567890));
		    telephones.put("mobile", new Long(1234567890));
		    
		    //Add map type
		    req.setTelephones(telephones);
		    
		    //Calulate Message Size
		    ByteArrayOutputStream out = new ByteArrayOutputStream();
		    Encoder encoder = EncoderFactory.get().binaryEncoder(out, null);
		    SpecificDatumWriter<RequestData> writer = new SpecificDatumWriter<RequestData>(RequestData.class);
		    try {
				writer.write(req, encoder);
				encoder.flush();
				System.out.println("1." + Thread.currentThread().getName()+": Calling (async) with "+out.size() +" bytes...");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		    
		    //Send Simple ASYNC message	    
		    final CallFuture<Response> future1 = new CallFuture<Response>();
		    try {
		    	Date requestTime = new Date();
				proxy.create(req, future1);
				System.out.println("2." + Thread.currentThread().getName()+": proxy.create(req, future1) returned");
				Response asyncResult = future1.get(); 
				System.out.println("3." + Thread.currentThread().getName()+": Callback<Response>.get() returned \"" + asyncResult + "\"");
				Date responseTime = new Date();
				
				//Get the time bewteen calls;
				long difference = responseTime.getTime()- requestTime.getTime();
				System.out.println("4." + Thread.currentThread().getName()+": This call has taken "+ difference+" miliseconds");
				count++;
				time+=difference;
			} catch (IOException | InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
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
