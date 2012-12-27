namespace cpp com.paradigmatecnologico.binaryrpc.thrift.model
namespace d com.paradigmatecnologico.binaryrpc.thrift.model
namespace java com.paradigmatecnologico.binaryrpc.thrift.model
namespace php com.paradigmatecnologico.binaryrpc.thrift.model
namespace perl com.paradigmatecnologico.binaryrpc.thrift.model

enum State {
   	MARRIED,
   	SINGLE,
   	UNDEFINED
}

//Defines simple Data Structure
struct Data {
	1: required double ID

	2: required string name
	
	3: optional State state = State.UNDEFINED
	
	4: required map<string,i64> telephones
	
	5: required list<string> addresses
	
	6: optional bool member
	
	7: optional string description	
}

struct Response {

	1: required i32 code;
		
	2: required string message;
		
	3: optional Data responseData;

}

//Defines simple service
service ThriftTestService {

	Response create(1:Data data),

}
