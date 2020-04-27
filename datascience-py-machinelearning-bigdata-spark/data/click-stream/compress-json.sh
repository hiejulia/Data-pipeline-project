#!/bin/bash

## compresses json files

mkdir -p 'json-gz'
for f in `ls json`
do
  input="json/$f"
  output="json-gz/$f.gz"
  echo "compressing $input --> $output"
  gzip < $input > $output
done