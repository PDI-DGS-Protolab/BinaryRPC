package com.paradigmatecnologico.binaryrpc.protobuf;


import java.util.Date;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.protobuf.RpcCallback;
import com.google.protobuf.RpcChannel;
import com.google.protobuf.RpcController;
import com.googlecode.protobuf.socketrpc.RpcChannels;
import com.googlecode.protobuf.socketrpc.RpcConnectionFactory;
import com.googlecode.protobuf.socketrpc.SocketRpcConnectionFactories;
import com.googlecode.protobuf.socketrpc.SocketRpcController;
import com.paradigmatecnologico.binaryrpc.protobuf.model.Entity;
import com.paradigmatecnologico.binaryrpc.protobuf.model.Entity.Data;
import com.paradigmatecnologico.binaryrpc.protobuf.model.Entity.Data.State;
import com.paradigmatecnologico.binaryrpc.protobuf.model.Entity.Data.Telephone;
import com.paradigmatecnologico.binaryrpc.protobuf.model.ProtoBufTestService.ProtobufTestService;
import com.paradigmatecnologico.binaryrpc.protobuf.model.Entity.Response;

public class ProtoBufTestClient implements Runnable {
	
	public ExecutorService threadPool;
	
	private Date requestTime;

	public void run() {
	    	//Populate sample data
	        Data data = populateData();
	    	
	        // Create a thread pool
	        threadPool = Executors.newFixedThreadPool(1);
	        
	        // Create channel
	        RpcConnectionFactory connectionFactory = SocketRpcConnectionFactories.createRpcConnectionFactory("127.0.0.1", 1984);
	        RpcChannel channel = RpcChannels.newRpcChannel(connectionFactory, threadPool);
	        
	        
	    	//Calculate Message Size
	        System.out.println("1." + Thread.currentThread().getName()+": Calling (async) with "+ data.getSerializedSize() +" bytes...");

	    	
	    	//Make asynchronous call, continue in CreateMethodCallback...
	        requestTime = new Date();
	        ProtobufTestService testService = ProtobufTestService.newStub(channel);
	        RpcController controller = new SocketRpcController();
	        
	        //CreateMethodCallback callback = new CreateMethodCallback();
//	        testService.create(controller,data, new RpcCallback<Entity.Response>() {
//
//				@Override
//				public void run(Entity.Response arg0) {
//					Response result = arg0;
//	                System.out.println("2." + Thread.currentThread().getName()+": client.create(data, new CreateMethodCallback()); returned");
//	                System.out.println("3." + Thread.currentThread().getName()+": Future<Response>.get() returned \"" + result.getMessage() + "\"");
//	                Date responseTime = new Date();
//	        		
//	        		//Get the time between calls;
//	        		long difference = responseTime.getTime()- requestTime.getTime();
//	        		System.out.println("4." + Thread.currentThread().getName()+": This call has taken "+ difference+" miliseconds");
//	        		
//	        		threadPool.shutdown();
//					
//				}
//			});
	        
	        // Check success
	        if (controller.failed()) {
	        	System.err.println("Rpc failed "+controller.errorText());
	        }
        	    
    }

    public static void main(String[] args) {
    	ProtoBufTestClient c = new ProtoBufTestClient();
        c.run();
    }
    
    
    /**
     * 
     * Callback Method for asynchronous communication
     * 
     * @author Paradigma
     *
     */
    public class CreateMethodCallback implements RpcCallback<Response> {
     
            public void onError(Exception e) {
                System.out.println("Error : ");
                e.printStackTrace();
            }

			@Override
			public void run(Response arg0) {
                Response result = arg0;
                System.out.println("2." + Thread.currentThread().getName()+": client.create(data, new CreateMethodCallback()); returned");
                System.out.println("3." + Thread.currentThread().getName()+": Future<Response>.get() returned \"" + result.getMessage() + "\"");
                Date responseTime = new Date();
        		
        		//Get the time between calls;
        		long difference = responseTime.getTime()- requestTime.getTime();
        		System.out.println("4." + Thread.currentThread().getName()+": This call has taken "+ difference+" miliseconds");
        		
        		threadPool.shutdown();
			}
     
        }
    
    /**
     * This Method populate simple Data class for testing proposes
     * 
     * @return Data
     */
    public static Data populateData(){
	    
    	//Populate data
	    Data data = Data.newBuilder()
	    		.addAddresses("Calle 1")
	    		.addAddresses("Calle 2")
	    		.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam et ipsum felis, sit amet tincidunt erat. Duis id mauris sit amet eros consectetur " +
		    					"posuere condimentum nec ipsum. Fusce nec est eu mauris fringilla facilisis. Cras a mauris feugiat eros lacinia molestie in non arcu. Maecenas " +
		    					"in felis eu augue ornare pharetra eget a mauris. Mauris mi metus, fringilla eu lobortis sed, convallis non turpis. Aenean mi justo, dapibus eget " +
		    					"accumsan quis, laoreet nec libero. Mauris ac turpis id elit cras amet.")
		    	.setID(new Random().nextDouble())
		    	.setMember(true)
		    	.setName("name01")
		    	.setState(State.SINGLE)
		    	.addTelephones(0, Telephone.newBuilder().setAlias("home").addNumber(new Long(1234567890)))
		    	.addTelephones(0, Telephone.newBuilder().setAlias("mobile").addNumber(new Long(1234567890)))
		    	.build();
	    return data;
    }
}
