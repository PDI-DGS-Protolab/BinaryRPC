package com.paradigmatecnologico.binaryrpc.messagePack;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.msgpack.MessagePack;
import org.msgpack.packer.Packer;
import org.msgpack.rpc.Client;
import org.msgpack.rpc.Future;
import org.msgpack.rpc.loop.EventLoop;

import com.paradigmatecnologico.binaryrpc.messagePack.model.Entity.Data;
import com.paradigmatecnologico.binaryrpc.messagePack.model.Entity.Response;
import com.paradigmatecnologico.binaryrpc.messagePack.model.Entity.State;


public class MessagePackTestClient implements Runnable {
	private static Client cli;

	//Interface for the RPC Calls
	public static interface RPCInterface {
        //Simple Call
		Future<Response> create(Data data);		
		void addTime(Long time);		
    }
 
    public void run() {
        EventLoop loop = EventLoop.defaultEventLoop();
 
        try {
			cli = new Client("localhost", 1984, loop);
		} catch (UnknownHostException e2) {
			e2.printStackTrace();
		}
        RPCInterface iface = cli.proxy(RPCInterface.class);
        
        Data data = populateData();
 
             
        
        //Calulate Message Size
        MessagePack msgpack = new MessagePack();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Packer packer = msgpack.createPacker(out);
	    try {
			packer.write(data);
			System.out.println("1." + Thread.currentThread().getName()+": Calling (async) with "+out.toByteArray().length +" bytes...");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        
        //Make the call
        Date requestTime = new Date();
        Future<Response> future1 = null;
        try {
        	future1 = iface.create(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        
        System.out.println("2." + Thread.currentThread().getName()+": iface.create(data) returned");
		//Async call
        Response asyncResult = null;
        try {
			asyncResult = future1.get(); 			
			System.out.println("3." + Thread.currentThread().getName()+": Future<Response>.get() returned \"" + asyncResult.message + "\"");
			Date responseTime = new Date();
			
			//Get the time bewteen calls;
			long difference = responseTime.getTime()- requestTime.getTime();
			System.out.println("4." + Thread.currentThread().getName()+": This call has taken "+ difference+" miliseconds");
			iface.addTime(difference);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			cli.close();
		}
		
    }
    
    
    /**
     * This Method populate simple Data class for testing proposes
     * 
     * @return Data
     */
    public static Data populateData(){
    	//Populate data
    	Data data = new Data(); 
	    
	    List<String> addresses =new ArrayList<String>();
	    addresses.add("Calle 1");
	    addresses.add("Calle 2");
	    //Add String Array
	    data.addresses=addresses;
	    //Add 500 bytes string
	    data.description="Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam et ipsum felis, sit amet tincidunt erat. Duis id mauris sit amet eros consectetur " +
	    					"posuere condimentum nec ipsum. Fusce nec est eu mauris fringilla facilisis. Cras a mauris feugiat eros lacinia molestie in non arcu. Maecenas " +
	    					"in felis eu augue ornare pharetra eget a mauris. Mauris mi metus, fringilla eu lobortis sed, convallis non turpis. Aenean mi justo, dapibus eget " +
	    					"accumsan quis, laoreet nec libero. Mauris ac turpis id elit cras amet.";
	    //Add double value
	    data.ID=new Random().nextDouble();
	    //Add Boolean value
	    data.member=true;
	    //Add String
	    data.name="name01";
	    //Add enum type
	    data.state=State.SINGLE;
	    
	    Map<String, Long> telephones = new HashMap<String,Long>(5);
	    telephones.put("home", new Long(1234567890));
	    telephones.put("mobile", new Long(1234567890));
	    
	    //Add map type
	    data.telephones=telephones;
	    
	    return data;
    }
}
