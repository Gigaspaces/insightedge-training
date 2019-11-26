# Lab: AnalyticXtreme

# Instructions

Edit spark.home in pu.xml (add your i9e home path)

Run mvn clean install -> ax-pu

Run fetchHiveJars.sh <path to ZIP you will run gs_home>

Run docker for hive - runHive.sh (make sure to install docker and docker composer before) -> use sudo

pip install docker -> to be able to import docker module from python.

Follow the docker post installation:
https://docs.docker.com/install/linux/linux-postinstall/

Run "python dnsthing.py" This script creates file hosts copy it's content to the end of your /etc/hosts file,Remember to do so everytime you stop/restart/kill your docker containers as their IP changes.
To debug python do --verbose

Create hive table - main in hive-initializer, and write to hive tables:
mvn exec:java -Dexec.mainClass="com.gigaspaces.HiveCreateTable"

Start insightedge with 2 GSC ./gs.sh host run-agent --auto --gsc=2

Deploy ax-pu jar:
./gs.sh pu deploy AxPu /home/vagrant/insightedge-training/Day5/20-data-lake-acceleration/Lab-19-Query-speed-batch/ax-pu/target/AxPu.jar

Deploy index space pu (gs-home/insightedge/lib/analytics-xtreme/batch-index/batch-index.jar

See existing types and data in index space using web-ui.
 
Preform queries - AXClient in remote-client module

Examine statistics and index distributions, see files created in your training home/logs

Write new entries to speed space Client in remote-client module

Go to zeppelin, import exercise notebook

Create new insightedge_jdbc_ax interpreter set url to jdbc:insightedge:url=jini://*/*/speedSpace;analyticsXtreme.enabled=true

Create new  jdbc interpreter called hive_jdbc and set its  url to hive2://hive-server:10000/;ssl=false in both interpr remember to add property com.gigaspaces.jdbc.autoCommit and set its value to true

Follow instructions in the notebook 
