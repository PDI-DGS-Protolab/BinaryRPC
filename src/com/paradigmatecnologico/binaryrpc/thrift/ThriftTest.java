package com.paradigmatecnologico.binaryrpc.thrift;

import java.io.BufferedReader;
import java.io.InputStreamReader;


public class ThriftTest {
	
	
	public static Integer count =0;
	public static long time = new Long(0);
	
	public static void main(String[] args) throws Exception {
		
		System.out.println("Starting server on port 1984 ...");

	    Thread threadS = new Thread(new ThiftTestServer(),"server");
	    threadS.start();
		// Uncomment this to launch 5 concurrent threads
//      Thread thread1 = new Thread(new MessagePackTestClient(),"client1");
//	    Thread thread2 = new Thread(new MessagePackTestClient(),"client2");
//	    Thread thread3 = new Thread(new MessagePackTestClient(),"client3");
//	    Thread thread4 = new Thread(new MessagePackTestClient(),"client4");
//	    thread1.start();	     
//	    thread2.start();	    
//	    thread3.start();	    
//	    thread4.start();	    
	    
	    System.out.println("Push e to exit or any key to launch a new call: ");
	    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	    Boolean looping= true;
	    Integer index = 1;
	    while (looping){
	    	String input = br.readLine();
	    	if (!"e".equals(input)){
	    		Thread thread1 = new Thread(new ThriftTestClient(),"client"+index);
	    	    thread1.start();
	    	    index++;
	    	}else{
	    		looping=false;;
	    	}
	    }
	    
	    //Thread.currentThread().sleep(120000);
	    System.out.println("The average time for "+count +" calls was: "+time/count);	    
	    System.out.println("...Exitting, bye");
	    ThiftTestServer.stop();
	    System.exit(0);
    }
}
