package com.paradigmatecnologico.binaryrpc.avro;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.avro.AvroRemoteException;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.paradigmatecnologico.binaryrpc.avro.service.RequestData;
import com.paradigmatecnologico.binaryrpc.avro.service.Response;
import com.paradigmatecnologico.binaryrpc.avro.service.Search;
import com.paradigmatecnologico.binaryrpc.avro.service.AvroTestService.Callback;

/**
 * 
 * @author Paradigma
 * 
 * This Class implements simple Avro server which manage asynchronous calls. Can manage creation or search calls
 *
 */
public class AvroTestServer implements Callback {
	
	private static DB db;
	  
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
