package com.paradigmatecnologico.binaryrpc.protobuf;

import java.io.BufferedReader;
import java.io.InputStreamReader;


public class ProtoBufTest {

public static void main(String[] args) throws Exception {
		
		System.out.println("Starting server on port 1984 ...");

	    Thread threadS = new Thread(new ProtoBufTestServer(),"server");
	    threadS.start();
		// Uncomment this to launch 5 concurrent threads
//      Thread thread1 = new Thread(new ProtoBufTestClient(),"client1");
//	    Thread thread2 = new Thread(new ProtoBufTestClient(),"client2");
//	    Thread thread3 = new Thread(new ProtoBufTestClient(),"client3");
//	    Thread thread4 = new Thread(new ProtoBufTestClient(),"client4");
//	    thread1.start();	     
//	    thread2.start();	    
//	    thread3.start();	    
//	    thread4.start();	    
	    
	    System.out.println("Push e to exit or any key to launch a new call: ");
	    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	    Boolean exit= true;
	    Integer index = 1;
	    while (exit){
	    	String input = br.readLine();
	    	if (!"e".equals(input)){
	    		Thread thread1 = new Thread(new ProtoBufTestClient(),"client"+index);
	    	    thread1.start();
	    	    index++;
	    	}else{
	    		exit=false;;
	    	}
	    }
	    
	    System.out.println("...Exitting, bye");
	    ProtoBufTestServer.stop();
	    System.exit(0);
    }
}
