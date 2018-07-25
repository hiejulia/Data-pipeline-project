#!/bin/bash 
while true; 
do  
# Get data from stock csv file 
curl "http://download.finance.yahoo.com/d/quotes.csv?s=RHT,MSFT,GOOG,INFY&f=sb2b3jk";  
sleep 2;  
done  