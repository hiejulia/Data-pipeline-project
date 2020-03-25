# Apache HBase cluster to EC2 using EMR 
+ Backup HBase data (S3)
+ EMR - create cluster - AMI 
+ HBase : connect to master node of deployed HBase cluster - start HBase shell
    + EMR : cluster detail - master public DNS name (ssh -i ec2.pem hadoop@ec2-184-72-138-2.compute-1.amazonaws.com)
    
    + `hbase shell`
    + `aws emr schedule-hbase-backup --cluster-id <cluster_id> \
 --type full –dir s3://hcb-c2-data/hbase-backup \
--interval 1 --unit hours`

+ Cluster - create new job flow 


# Create Amazon EMR job flow 
+ Create EMR cluster 
    + aws emr create-cluster --ami-version 3.1.0 \
--log-uri s3://hcb-c2-logs \
--instance-groups \
InstanceGroupType=MASTER,InstanceCount=1,\
InstanceType=m3.xlarge \
InstanceGroupType=CORE,InstanceCount=2,\
InstanceType=m3.xlarge
{
    “ClusterId”: “j-2X9TDN6T041ZZ”
}


