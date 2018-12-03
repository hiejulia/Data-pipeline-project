

# Create RDD  - sc.parallelizee -
val valintRDD = sc.parallelize(Array(1,4,5,6,7,10,15));




# Filter even number 
val valevenNumberRDDFilter = valintRDD.filter(i => (i%2 == 0 ));


# Map number
val sum = valevenNumberRDDFilter.sum;



# Reduce number 

# Get the sum 
sum 