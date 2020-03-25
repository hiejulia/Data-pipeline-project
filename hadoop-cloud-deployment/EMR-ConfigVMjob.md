# Using EMR bootstrap actions to configure VMs for the Amazon EMR jobs

+ Add bootstrap action - config tab - 
    + run script : `dowload.sh`

+ Predefined bootstrap actioons
    + `config-daemons`: set JVM options - heap size, GC 
    + `config-hadoop`
    + `memory-intensive`: config hadoop cluster 
    + `run-if`