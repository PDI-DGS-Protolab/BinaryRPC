package com.paradigmatecnologico.binaryrpc.avro;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.ipc.CallFuture;
import org.apache.avro.specific.SpecificDatumWriter;

import com.paradigmatecnologico.binaryrpc.avro.service.RequestData;
import com.paradigmatecnologico.binaryrpc.avro.service.Response;
import com.paradigmatecnologico.binaryrpc.avro.service.State;

/**
 * 
 * This Class is a sample Runnable for make simple creation calls to the server
 * 
 * @author Paradigma
 *
 */
public class AvroClientRunnable implements Runnable{

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
			AvroTest.proxy.create(req, future1);
			System.out.println("2." + Thread.currentThread().getName()+": proxy.create(req, future1) returned");
			Response asyncResult = future1.get(); 
			System.out.println("3." + Thread.currentThread().getName()+": Callback<Response>.get() returned \"" + asyncResult + "\"");
			Date responseTime = new Date();
			
			//Get the time bewteen calls;
			long difference = responseTime.getTime()- requestTime.getTime();
			System.out.println("4." + Thread.currentThread().getName()+": This call has taken "+ difference+" miliseconds");
			AvroTest.count++;
			AvroTest.time+=difference;
		} catch (IOException | InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		
	}
	
}
