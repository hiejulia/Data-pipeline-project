# Data serialization options 



## Description 
+ Process of converting data in memory to bytes 
+ Serialization framework : Avro, Thrft



## Apache Avro 
+ Serialize and deserialize records
+ Schema-based serialization framework - store data using Avro data files 
+ JSON 
    {
    "type": "record",
    "name": "Catalog",
    "fields": [
        {"name": "name", "type": "string"},
        {"name": "id", "type": "int"},
        {"name": "journal", "type": "string"},
        {"name": "editions", "type": {"type": "array", "items": "string"}}
    ]
}

+ data type : binary and json 











## Apache Thrift 



mat no tu duy ucuc ki nhieu - the toi 