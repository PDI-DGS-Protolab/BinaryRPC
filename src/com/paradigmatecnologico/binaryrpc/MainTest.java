/**
 * 
 */
package com.paradigmatecnologico.binaryrpc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.paradigmatecnologico.binaryrpc.avro.AvroTest;
import com.paradigmatecnologico.binaryrpc.messagePack.MessagePackTest;
import com.paradigmatecnologico.binaryrpc.protobuf.ProtoBufTest;
import com.paradigmatecnologico.binaryrpc.thrift.ThriftTest;

/**
 * 
 * 
 * 
 * @author Paradigma
 *
 */
public class MainTest {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		System.out.println("***************************");
		System.out.println("*     Binary RPC Test     *");
		System.out.println("*                         *");
		System.out.println("***************************");
		System.out.println();
		System.out.println("Press a number to start the test, or other key to exit");
		System.out.println("1. Apache Avro Test");
		System.out.println("2. Message Pack Test");
		System.out.println("3. Apache Thrift Test");
		System.out.println("4. Protocol Buffers Test (Serialization/Deserialization Only)");
		System.out.println();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input = "";
		try {
			input = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		switch (input) {
		case "1":
			AvroTest.main(new String[]{});
			break;
		case "2":
			MessagePackTest.main(new String[]{});
			break;
		case "3":
			ThriftTest.main(new String[]{});
			break;
		case "4":
			ProtoBufTest.main(new String[]{});
			break;
		default:
			System.out.println("Exitting, bye...");
			break;
		}
		
		
		

	}

}
