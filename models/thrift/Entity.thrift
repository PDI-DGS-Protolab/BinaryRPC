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


struct Data {
	required double ID

	required string name
	
	optional State state = State.UNDEFINED
	
	required map<string,i64> telephones
	
	required list<string> addresses
	
	optional bool member
	
	optional string description	
}