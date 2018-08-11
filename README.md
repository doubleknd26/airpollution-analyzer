## Airpollution Analyzer
This is my first hadoop mapreduce program to analyze air pollution of Seoul, South Korea.  
There is two modules in this project. First, *airpollution-analyzer-mapreduce* is a module to run hadoop mapreduce. 
The other one is a *airpollution-analyzer-service* and it is a module to see mapreduce result using http.

### Getting started with airpollution-analyzer-mapreduce

Step1) Install hadoop.  
1. Install [brew](https://brew.sh).
2. Enter this command line 'brew install hadoop'. Then you can see it.  ![brew install hadoop](images/hadoop_install.png){ width=50% }
3. After installation, you can see hadoop directory in /usr/local/Cellar/hadoop/3.1.0
4. To access hadoop bin in everywhere, setup HADOOP_HOME in ~/.bash_profile  ![bash_profile](images/bash_profile.png)
5. Go to $HADOOP_HOME/etc/hadoop/hadoop-env.sh and add only JAVA_HOME absolute path like this. ![hadoop-env](images/hadoop-env.png)
6. Go to $HADOOP_HOME/etc/hadoop/core-site.xml and add below configurations.
   1. ```
      <configuration>
        <property>
          <name>fs.defaultFS</name>
          <value>hdfs://localhost:9000</value>
        </property>
        <property>
          <name>hadoop.tmp.dir</name>
          <value>/usr/local/Cellar/hadoop/data/</value>
        </property>
      </configuration>
      ```
7. Go to $HADOOP_HOME/etc/hadoop/hdfs-site.xml and add below configurations.
   1. ```
      <configuration>
        <property>
          <name>dfs.replication</name>
          <value>1</value>
        </property>
        <property>
          <name>dfs.http.address</name>
          <value>localhost:50070</value>
        </property>
        <property>
          <name>dfs.secondary.http.address</name>
          <value>localhost:50090</value>
        </property>
      </configuration>
      ```
8. Go to $HADOOP_HOME and run ./bin/hdfs namenode -format.
9. Go to $HADOOP_HOME and run ./sbin/start-dfs.sh. ![start-dfs](images/start-dfs.png)
10. Then, you can browse the web interface for the NameNode http://localhost:50070/. ![namenode_web](images/namenode_web.png)

