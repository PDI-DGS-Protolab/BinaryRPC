package com.paradigmatecnologico.binaryrpc.messagePack;

import java.io.BufferedReader;
import java.io.InputStreamReader;



public class MessagePackTest {
	
	public static void main(String[] args) throws Exception {
		
		System.out.println("**************************");
		System.out.println("     MessagePack Test     ");
		System.out.println("**************************");
		System.out.println();
		System.out.println("Starting server on port 1984 ...");
		MessagePackTestServer.run();
		   
	    
		System.out.println("Push 1 to lauch a new call");
	    System.out.println("Push 2 to lauch a batch test (1000 simultaneous calls)");
	    System.out.println("Push any other key to exit");
	    
	    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	    Boolean looping= true;
	    Integer index = 1;
	    while (looping){
	    	String input = br.readLine();
	    	if ("1".equals(input)){
	    		Thread thread1 = new Thread(new MessagePackTestClient(),"client"+index);
	    	    thread1.start();
	    	    index++;
	    	}else{
	    		if ("2".equals(input)){
	    			index=1;
	    			for (int i = 0; i < 1000; i++) {
						Thread thread1 = new Thread(new MessagePackTestClient(),"client"+i);
						thread1.start();
					}
	    			looping=false;
	    		}else{	    			
	    			looping=false;
	    		}	    		
	    	}
	    }
	    
//	    Thread.currentThread().sleep(240000);
	    if (MessagePackTestServer.count>0){
	    	System.out.println("The average time for "+MessagePackTestServer.count +" calls was: "+MessagePackTestServer.time/MessagePackTestServer.count);	    	
	    }
	    System.out.println("...Exitting, bye");
	    MessagePackTestServer.stop();
	    System.exit(0);
    }
}
