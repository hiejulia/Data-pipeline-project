# Spark for machine learning 
+ Apache spark from Machine learning ML - data analytics - 
+ Spark computing - Map Reduce platform 
+ Spark RDD - Spark pipeline - Data frame



# Create data frame 
sqlContext <- sparkRSQL.init(sc)
To create a Spark dataframe, users may perform the following:

sqlContext <- SQLContext(sc)

df <- jsonFile(sqlContext, "examples/src/main/resources/people.json")
# Displays the content of the DataFrame to stdout- display the content of DataFrame to stdout 
showDF(df)
For Spark dataframe operations, the following are some examples:

sqlContext <- sparkRSQL.init(sc)
# Create the DataFrame
df <- jsonFile(sqlContext, "examples/src/main/resources/people.json")
# Show the content of the DataFrame
showDF(df)
## age  name
## null Michael
## 30   Andy
## 19   Justin

# Print the schema in a tree format
printSchema(df)
## root
## |-- age: long (nullable = true)
## |-- name: string (nullable = true)

# Select only the "name" column
showDF(select(df, "name"))
## name
## Michael
## Andy
## Justin

# Select everybody, but increment the age by 1
showDF(select(df, df
$name, df$age + 1))
## name    (age + 1)
## Michael null
## Andy    31
## Justin  20

# Select people older than 21
showDF(where(df, df$age > 21))
## age name
## 30  Andy
# Count people by age 
showDF(count(groupBy(df, "age")))
## age  count
## null 1
## 19   1
## 30   1
Note





