# Data serialization options 



## Description 
+ Process of converting data in memory to bytes 
+ Serialization framework : Avro, Thrft



## Apache Avro 
+ Serialize and deserialize records
+ Schema-based serialization framework - store data using Avro data files 
+ JSON 
    







## Apache Thrift 


// ba ay co make up 
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
Subsequently modify the schema to the following:
{
    "type": "record",
    "name": "Catalog",
    "fields": [
        {"name": "name", "type": "string"},
        {"name": "id", "type": "string"},
        {"name": "publisher", "type": "string"},
        {"name": "editions", "type": {"type": "array", "items": "string"}}
    ]
}
The modified schema may be used for deserialization of the binary data.
Avro offers two choices for encoding data when serializing: binary and JSON. In the binary format the schema is included at the beginning of the file and the binary data is serialized untagged resulting in a smaller data file. Using a schema, the binary data may be encoded/decoded in JSON. The flexibility of converting between binary and JSON formats makes Avro a suitable serialization framework in a scenario in which the priorities/requirements are schema flexibility and serialization performance resulting from a compact binary file. Avro supports generic data handling with the GenericData.Record key abstraction , which is a set of key/value pairs in which the name is the field name and the value is one of the Avro supported data types. Instantiating a generic record involves providing a JSON-encoded schema definition and fields are accessed using put/get methods. The “generic” approach to data handling simplifies the code base in contrast to the “static” code generation. Avro’s dynamic typing provides serialization and deserialization without code generation.
Serialization for Avro types is provided using the org.apache.hadoop.io.serializer.avro.AvroSerialization<T> class, which implements the org.apache.hadoop.io.serializer.Serialization<Writable> interface. AvroSerialization delegates to Writable.write(java.io.DataOutput) and Writable.readFields(java.io.DataInput). The org.apache.avro.hadoop.io.AvroSequenceFile class is a wrapper around SequenceFile that supports serializing Avro data. AvroSequenceFile is implemented as follows:
Avro key data is wrapped in the AvroKey object.
Avro value data is wrapped in the AvroValue object.
AvroSerialization is configured and registered with SerializationFactory to accept only objects that are instances of AvroKey or AvroValue.
Comparing Sequence Files and Avro
Both SequenceFile and Avro are binary formats and have native support in Hadoop. Neither require code generation.
Avro data files are smaller size and compact in comparison to SequenceFiles. Avro supports complex data structures such as records, ar