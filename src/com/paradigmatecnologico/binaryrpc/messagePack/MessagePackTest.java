package com.paradigmatecnologico.binaryrpc.messagePack;

import java.io.BufferedReader;
import java.io.InputStreamReader;




public class MessagePackTest {
	
	public static void main(String[] args) throws Exception {
		
		System.out.println("Starting server on port 1984 ...");
		MessagePackTestServer.run();
		   
	    
	    System.out.println("Push e to exit or any key to launch a new call: ");
	    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	    Boolean looping= true;
	    Integer index = 1;
	    while (looping){
	    	String input = br.readLine();
	    	if (!"e".equals(input)){
	    		Thread thread1 = new Thread(new MessagePackTestClient(),"client"+index);
	    	    thread1.start();
	    	    index++;
	    	}else{
	    		looping=false;;
	    	}
	    }
	    
//	    Thread.currentThread().sleep(240000);
	    System.out.println("The average time for "+MessagePackTestServer.count +" calls was: "+MessagePackTestServer.time/MessagePackTestServer.count);
	    System.out.println("...Exitting, bye");
	    MessagePackTestServer.stop();
	    System.exit(0);
    }
}
