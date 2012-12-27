package com.paradigmatecnologico.binaryrpc.thrift;

import java.net.UnknownHostException;

import org.apache.thrift.TException;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.paradigmatecnologico.binaryrpc.thrift.model.Data;
import com.paradigmatecnologico.binaryrpc.thrift.model.Response;
import com.paradigmatecnologico.binaryrpc.thrift.model.ThriftTestService;

public class ThriftTestServiceImp implements ThriftTestService.Iface {

	public static DB db;
	
	@Override
	public Response create(Data data) throws TException {
		// connect to mongoDB, ip and port number
     	Mongo mongo=null;
		try {
			mongo = new Mongo("localhost", 27017);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
     	// get database from MongoDB,
     	// if database doesn't exists, mongoDB will create it automatically
     	db = mongo.getDB("test");
     	
     	Response response= new Response();
     	
     	try {
			//Slow the execution for test asynchronous calls, if neccesary
			//Thread.sleep((long) (new Random().nextDouble()*10000));
			
			DBCollection coll = db.getCollection("testCollection");
			

			BasicDBObject doc = new BasicDBObject("ID", data.ID).
					append("name", data.getName()).
					append("state", data.getState().getValue()).
					append("addresses", data.getAddresses()).
					append("telephones", data.getTelephones()).
					append("member", data.isMember()).
					append("description", data.getDescription());
			
			coll.insert(doc);
			
			//Format the confirmation message
			response.setResponseData(data);
			response.setCode(200);
			ObjectId id = (ObjectId)doc.get( "_id" );
			response.setMessage("created with Database id = "+id.toString());
			
			return response;
		} catch (Exception e) {
			
			response.setCode(500);
			response.setMessage(e.getMessage());
			return response;
		}finally{
			mongo.close();
		}
	}

}
