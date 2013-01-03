package com.paradigmatecnologico.binaryrpc.protobuf;

import java.io.IOException;
import java.util.concurrent.Executors;

import com.googlecode.protobuf.socketrpc.RpcServer;
import com.googlecode.protobuf.socketrpc.ServerRpcConnectionFactory;
import com.googlecode.protobuf.socketrpc.SocketRpcConnectionFactories;


public class ProtoBufTestServer implements Runnable {

	public static ServerRpcConnectionFactory   rpcConnectionFactory ;
	private static RpcServer  server;

	public void start() {
		rpcConnectionFactory = SocketRpcConnectionFactories.createServerRpcConnectionFactory(1984);
	    server = new RpcServer(rpcConnectionFactory, Executors.newFixedThreadPool(1), true);
	    server.registerService(new ProtoBufTestServiceImpl());
	    server.run();
	}
		 
	public static void stop() {
		server.shutDown();
		try {
			rpcConnectionFactory.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		ProtoBufTestServer srv = new ProtoBufTestServer();
	    srv.start();
	}

	@Override
	public void run() {
		start();
		
	}
}
