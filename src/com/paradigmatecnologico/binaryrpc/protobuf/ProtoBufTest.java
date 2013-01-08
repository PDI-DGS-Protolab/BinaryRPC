package com.paradigmatecnologico.binaryrpc.protobuf;

import java.io.BufferedReader;
import java.io.InputStreamReader;


public class ProtoBufTest {

public static void main(String[] args) throws Exception {
		
		System.out.println("******************************");
		System.out.println("     Protocol Buffer Test     ");
		System.out.println("******************************");
		System.out.println();
		System.out.println("Starting server on port 1984 ...");

	    Thread threadS = new Thread(new ProtoBufTestServer(),"server");
	    threadS.start();   
	    
	    System.out.println("Push e to exit or any key to launch a new call: ");
	    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	    Boolean looping= true;
	    Integer index = 1;
	    while (looping){
	    	String input = br.readLine();
	    	if (!"e".equals(input)){
	    		Thread thread1 = new Thread(new ProtoBufTestClient(),"client"+index);
	    	    thread1.start();
	    	    index++;
	    	}else{
	    		looping=false;;
	    	}
	    }
	    
	    System.out.println("...Exitting, bye");
	    ProtoBufTestServer.stop();
	    System.exit(0);
    }
}
