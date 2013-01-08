package com.paradigmatecnologico.binaryrpc.thrift;

import java.io.BufferedReader;
import java.io.InputStreamReader;


public class ThriftTest {
	
	
	public static Integer count =0;
	public static long time = new Long(0);
	
	public static void main(String[] args) throws Exception {
		
		System.out.println("****************************");
		System.out.println("     Apache Thrift Test     ");
		System.out.println("****************************");
		System.out.println();
		System.out.println("Starting server on port 1984 ...");

	    Thread threadS = new Thread(new ThiftTestServer(),"server");
	    threadS.start();

	    System.out.println("Push 1 to lauch a new call");
	    System.out.println("Push 2 to lauch a batch test (1000 simultaneous calls)");
	    System.out.println("Push 3 to show times and reset counters");
	    System.out.println("Push any other key to exit");
	    
	    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	    Boolean looping= true;
	    Integer index = 1;
	    String input="";
	    while (looping){
	    	input = br.readLine();
	    	switch (input) {
			case "1":
				Thread thread1 = new Thread(new ThriftTestClient(),"client"+index);
	    	    thread1.start();
	    	    index++;
				break;
			case "2":
				index=1;
    			for (int i = 0; i < 1000; i++) {
					Thread thread2 = new Thread(new ThriftTestClient(),"client"+i);
					thread2.start();
				}
				break;
			case "3":
				if (count>0){
			    	System.out.println("The average time for "+count +" calls was: "+time/count);	    	
			    }
				count=0;
				time=new Long(0); 
				break;

			default:
				looping=false;
				break;
			}
	    }
	    
	    //Thread.currentThread().sleep(120000);
	    if (count>0){
	    	System.out.println("The average time for "+count +" calls was: "+time/count);
	    }
	    System.out.println("...Exitting, bye");
	    ThiftTestServer.stop();
	    System.exit(0);
    }
}
