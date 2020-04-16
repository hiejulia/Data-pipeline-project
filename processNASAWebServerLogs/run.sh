 /usr/local/Cellar/apache-spark/2.4.5/libexec/bin/spark-submit \
  --class "SimpleApp" \
  --master "local[4]" \
  target/scala-2.12/nasa-weblogs_2.12-0.1.0-SNAPSHOT.jar 