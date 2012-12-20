BinaryRPC
=========

## Description ##

A prototype to check the performance and operation of a number of binary serialization technologies. This prototype developed in Java will consist of a client and a server communicating through different RPCs.

Each package contains a simple test in each technology. The test include message serialization/deserialization, and send and receive messages. The test also take simple measurements of the size of messages and several times. 

## Data Model ##

We have made a simple data model for test purposes. This model include the most common data types, like integer, strings, doubles and complex data structure as maps, arrays and enumerations.

The model, in JSON, look like this:

    {"type": "record",
     "name": "Data",
     "fields": [
     {"name": "ID", "type": "double"},
     {"name": "name", "type": "string"},
     {"name": "state", "type": {
     							 "name": "State",
    							 "type": "enum", 
     							 "symbols" : ["MARRIED", "SINGLE", "UNDEFINED"],
     							 "default": "UNDEFINED"}
     							},
     {"name": "telephones", "type": {
     								 "type": "map", 
     								 "values": "long"}
     								 },
     {"name": "addresses", "type": {
     								"type": "array", 
     								"items": "string"}
     								},
     {"name": "member", "type": ["boolean","null"]},
     {"name": "description", "type": ["string","null"]}
     ]
    }