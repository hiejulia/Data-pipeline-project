CREATE EXTERNAL TABLE 
hdi(
    id INT, 
    country STRING, 
    hdi FLOAT, 
    lifeex INT, 
    mysch INT, 
    eysch INT, 
    gni INT) 
ROW FORMAT DELIMITED 
FIELDS TERMINATED BY ',' 
STORED AS TEXTFILE
LOCATION '${INPUT}';

CREATE EXTERNAL TABLE 
output_countries(
    country STRING, 
    gni INT) 
    ROW FORMAT DELIMITED
    FIELDS TERMINATED BY ','
    STORED AS TEXTFILE
    LOCATION '${OUTPUT}';

INSERT OVERWRITE TABLE 
output_countries
  SELECT 
    country, gni 
  FROM 
    hdi 
  WHERE 
    gni > 2000;
