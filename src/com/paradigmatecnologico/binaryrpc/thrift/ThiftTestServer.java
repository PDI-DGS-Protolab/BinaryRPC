package com.paradigmatecnologico.binaryrpc.thrift;

import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.apache.thrift.transport.TTransportException;

import com.paradigmatecnologico.binaryrpc.thrift.model.ThriftTestService;

public class ThiftTestServer implements Runnable {
	
	public static TNonblockingServerTransport  serverTransport;
	private static TNonblockingServer server;

	public void start() {
		try {
			serverTransport = new TNonblockingServerSocket(1984);
	        ThriftTestService.Processor<ThriftTestServiceImp> processor = new ThriftTestService.Processor<ThriftTestServiceImp>(new ThriftTestServiceImp());
	        
	        server = new TNonblockingServer(new TNonblockingServer.Args(serverTransport).processor(processor));
	        server.serve();
	    } catch (TTransportException e) {
	       e.printStackTrace();
	    }
	}
		 
	public static void stop() {
		server.stop();
		serverTransport.close();
	}
	
	public static void main(String[] args) {
		ThiftTestServer srv = new ThiftTestServer();
	    srv.start();
	}

	@Override
	public void run() {
		start();
		
	}


}
