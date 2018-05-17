# E-commerce store 
Datasets from e-commerce store 
+ Statistical approach using Spark SQL 
+ Apriori algo 


### Dataset 
+ Massive datasets `cars` entity in JSON saved 
+ http://www.carqueryapi.com/api/0.3/?callback=?&cmd=getMakes
+ Attribute name : make_id, make_display, make_is_common, make_country
+ Row of the dataset 


### Step 
+ Data cleaning and mungling 
    + Filter unwanted data : Apache Spark filter
    + Handling incomplete or missing data : analyze datasets, 
handling missing data 
    + Discard data 
    + Fill constant value 
    + Populate average value 
    + Nearest neighbor approach : KNN algo , 
    + Convert data to a proper format : 

### Spark SQL : spark module for structured data processing 
+ SQL queries 
+ Building SparkConf and context 
+ Dataframe and datasets 
    + Dataframe : JSON, CSV, Hive table 
+ Load and parse data 
    + Spark API
    + Load data from sources to a table 
    + Register dataframe as a temporary view 
+ Analyzing data - the Spark SQL way 
    + Filter on data 
    + Select data 
        + Fetch 
        + Collect all data and print it 
        + Total count from the datasets 
        + Spark SQL 
        + Apriori algo : 
            + Item frequency 
            + Support 