package com.paradigmatecnologico.binaryrpc.protobuf;

import java.net.UnknownHostException;

import org.bson.types.ObjectId;

import com.google.protobuf.RpcCallback;
import com.google.protobuf.RpcController;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.paradigmatecnologico.binaryrpc.protobuf.model.Entity.Data;
import com.paradigmatecnologico.binaryrpc.protobuf.model.Entity.ProtobufTestService;
import com.paradigmatecnologico.binaryrpc.protobuf.model.Entity.Response;

public class ProtoBufTestServiceImpl extends ProtobufTestService {

	public static DB db;

	@Override
	public void create(RpcController controller, Data request,RpcCallback<Response> done) {
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
     	
     	
     	try {
			//Slow the execution for test asynchronous calls, if neccesary
			//Thread.sleep((long) (new Random().nextDouble()*10000));
			DBCollection coll = db.getCollection("testCollection");
			

			BasicDBObject doc = new BasicDBObject("ID", request.getID()).
					append("name", request.getName()).
					append("state", request.getState().name()).
					append("addresses", request.getAddressesList()).
					append("telephones", request.getTelephonesList()).
					append("member", request.getMember()).
					append("description", request.getDescription());
			
			coll.insert(doc);
			
			//Format the confirmation message
			
			ObjectId id = (ObjectId)doc.get( "_id" );
			Response response= Response.newBuilder()
					.setCode(200)
					.setMessage("created with Database id = "+id.toString())
					.setResponseData(request)
					.build();

			done.run(response);
		} catch (Exception e) {
			
			Response response= Response.newBuilder()
					.setCode(500)
					.setMessage(e.getMessage())
					.setResponseData(request)
					.build();

			done.run(response);
		}finally{
			mongo.close();
		}
		
	}

}
