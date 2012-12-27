package com.paradigmatecnologico.binaryrpc.messagePack;

import org.bson.types.ObjectId;
import org.msgpack.rpc.Server;
import org.msgpack.rpc.loop.EventLoop;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.paradigmatecnologico.binaryrpc.messagePack.model.Entity.Data;
import com.paradigmatecnologico.binaryrpc.messagePack.model.Entity.Response;

public class MessagePackTestServer {

	public static Server svr;

	public static DB db;
	
	private static EventLoop loop;

	
	// Asynchronous call
    public Response create(Data data) {
    	Response response= new Response();
		
		try {
			//Slow the execution for test asynchronous calls, if neccesary
			//Thread.sleep((long) (new Random().nextDouble()*10000));
			
			DBCollection coll = db.getCollection("testCollection");
			

			BasicDBObject doc = new BasicDBObject("ID", data.ID).
					append("name", data.name).
					append("state", data.state.name()).
					append("addresses", data.addresses).
					append("telephones", data.telephones).
					append("member", data.member).
					append("description", data.description);
			
			coll.insert(doc);
			
			//Format the confirmation message
			response.responseData=data;
			response.code=200;
			ObjectId id = (ObjectId)doc.get( "_id" );
			response.message = "created with Database id = "+id.toString();
			
			return response;
		} catch (Exception e) {
			
			response.code=500;
			response.message=e.getMessage();
			return response;
		}
    }
 
    public static void run() throws Exception {
        loop = EventLoop.defaultEventLoop();
        
        // connect to mongoDB, ip and port number
     	Mongo mongo = new Mongo("localhost", 27017);
     	
     	// get database from MongoDB,
     	// if database doesn't exists, mongoDB will create it automatically
     	db = mongo.getDB("test");
 
        svr = new Server(loop);
        svr.serve(new MessagePackTestServer());
        svr.listen(1984);
 
    }
    
    public static void stop() {
    	svr.close();
    	loop.shutdown();
    }
}
