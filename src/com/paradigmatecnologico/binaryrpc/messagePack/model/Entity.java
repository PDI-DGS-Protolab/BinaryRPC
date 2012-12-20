package com.paradigmatecnologico.binaryrpc.messagePack.model;

import java.util.List;
import java.util.Map;

import org.msgpack.annotation.Message;
import org.msgpack.annotation.Optional;

public class Entity {
	
	@Message
	public static class Data {
		public float ID;

		public String name;

	  	@Optional
		public String state = "UNDEFINED";

	  	public Map<String, Integer> telephones;

		public List<String> addresses;

		@Optional
		public Boolean member;
		
		@Optional
		public String description = "";
    }

}
