package com.paradigmatecnologico.binaryrpc.messagePack.model;

import java.util.List;
import java.util.Map;

import org.msgpack.annotation.Message;
import org.msgpack.annotation.Optional;
import org.msgpack.annotation.OrdinalEnum;

public class Entity {
	
	@Message
	public static class Data {
		public double ID;

		public String name;

	  	@Optional
		public State state = State.UNDEFINED;

	  	public Map<String, Long> telephones;

		public List<String> addresses;

		@Optional
		public Boolean member;
		
		@Optional
		public String description = "";
    }
	
	@Message
	public static class Response {
		public Integer code;
		
		public String message;
		
		public Data responseData;
	}
	
	@Message
	@OrdinalEnum
	public static enum State{
		MARRIED,SINGLE,UNDEFINED
	}

}
