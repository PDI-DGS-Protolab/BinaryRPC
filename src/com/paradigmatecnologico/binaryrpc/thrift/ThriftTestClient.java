package com.paradigmatecnologico.binaryrpc.thrift;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.thrift.TException;
import org.apache.thrift.TSerializer;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.async.TAsyncClientManager;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TNonblockingSocket;
import org.apache.thrift.transport.TTransportException;

import com.paradigmatecnologico.binaryrpc.thrift.model.Data;
import com.paradigmatecnologico.binaryrpc.thrift.model.Response;
import com.paradigmatecnologico.binaryrpc.thrift.model.State;
import com.paradigmatecnologico.binaryrpc.thrift.model.ThriftTestService;
import com.paradigmatecnologico.binaryrpc.thrift.model.ThriftTestService.AsyncClient.create_call;

public class ThriftTestClient implements Runnable {
	
	public ThriftTestService.AsyncClient client;
	
	private Date requestTime;

	public void run() {
	    try {
	    	//Populate sample data
	        Data data = populateData();
	    	
	        //Create new client
	    	try {
				client = new ThriftTestService.AsyncClient(new TBinaryProtocol.Factory(), new TAsyncClientManager(), new TNonblockingSocket("localhost", 1984));
			} catch (IOException e) {
				e.printStackTrace();
			}
	    	//Calculate Message Size
	    	byte[] empDtl = null;
	        TSerializer serializer = new TSerializer();
	        try {
	        	empDtl = serializer.serialize(data);
	        	//System.out.println("1." + Thread.currentThread().getName()+": Calling (async) with "+ empDtl.length +" bytes...");
	        } catch (TException e) {
	            e.printStackTrace();
	        } 
	    	
	    	
	    	//Make asynchronous call, continue in CreateMethodCallback...
	        requestTime = new Date();
	        client.create(data, new CreateMethodCallback());

        } catch (TTransportException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
    	ThriftTestClient c = new ThriftTestClient();
        c.run();
    }
    
    
    /**
     * 
     * Callback Method for asynchronous communication
     * 
     * @author Paradigma
     *
     */
    public class CreateMethodCallback implements AsyncMethodCallback<ThriftTestService.AsyncClient.create_call> {
     
            public void onError(Exception e) {
                System.out.println("Error : ");
                e.printStackTrace();
            }

			@Override
			public void onComplete(create_call response) {
				try {
                    Response result = response.getResult();
                    //System.out.println("2." + Thread.currentThread().getName()+": client.create(data, new CreateMethodCallback()); returned");
                    //System.out.println("3." + Thread.currentThread().getName()+": Future<Response>.get() returned \"" + result.getMessage() + "\"");
                    Date responseTime = new Date();
        			
        			//Get the time between calls;
        			long difference = responseTime.getTime()- requestTime.getTime();
        			//System.out.println("4." + Thread.currentThread().getName()+": This call has taken "+ difference+" miliseconds");
        			ThriftTest.count++;
        			ThriftTest.time+=difference;
                } catch (TException e) {
                    e.printStackTrace();
                }
				
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
	    data.setAddresses(addresses);
	    //Add 500 bytes string
	    data.description="Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam et ipsum felis, sit amet tincidunt erat. Duis id mauris sit amet eros consectetur " +
	    					"posuere condimentum nec ipsum. Fusce nec est eu mauris fringilla facilisis. Cras a mauris feugiat eros lacinia molestie in non arcu. Maecenas " +
	    					"in felis eu augue ornare pharetra eget a mauris. Mauris mi metus, fringilla eu lobortis sed, convallis non turpis. Aenean mi justo, dapibus eget " +
	    					"accumsan quis, laoreet nec libero. Mauris ac turpis id elit cras amet.";
	    //Add double value
	    data.setID(new Random().nextDouble());
	    //Add Boolean value
	    data.setMember(true);
	    //Add String
	    data.setName("name01");
	    //Add enum type
	    data.setState(State.SINGLE);
	    
	    Map<String, Long> telephones = new HashMap<String,Long>(5);
	    telephones.put("home", new Long(1234567890));
	    telephones.put("mobile", new Long(1234567890));
	    
	    //Add map type
	    data.setTelephones(telephones);
	    
	    return data;
    }

}



