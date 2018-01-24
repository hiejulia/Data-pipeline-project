#!/bin/bash
csvFile = "ibm-stock.csv" 
stockData = sc.textFile(csvFile) 
pair = stockData.map(lambda x:(x.split("-")[0],x.split(","[1])))
sumCount = pair.combineByKey(lambda value: (value, 1), lambda x, value: (float(x[0]) + float(value), x[1] + 1),lambda x, y: (x[0] + y[0], x[1] + y[1])) 
avgByKey = sumCount.map(lambda (label,(value_sum,count)):(label,value_sum.count))
# print 
avgByKey.sortByKey().collect();