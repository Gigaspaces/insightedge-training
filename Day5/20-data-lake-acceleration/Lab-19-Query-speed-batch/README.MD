# Lab: AnalyticXtreme

# Instructions

**1.** From project base dir Run:<br>
        `mvn clean install` 

**2.** Run docker for hive - `runHive.sh` (make sure to install docker and docker composer before) -> use sudo

**3.** Run "python dnsthing.py" This script creates file hosts copy it's content to the end of your /etc/hosts file,<br>
        Remember to do so everytime you stop/restart/kill your docker containers as their IP changes.<br>
**Note:** <br>
To debug python do --verbose <br>
In case that the docker module import from python doesn't work perform:<br>
 a. `sudo apt install python-pip` <br>
 b. `pip install docker`<br>
 c. Follow the docker post installation:<br>
    https://docs.docker.com/install/linux/linux-postinstall/

**4.** Create hive table - main in hive-initializer, and write to hive tables:<br>
    `mvn exec:java -Dexec.mainClass="com.gigaspaces.HiveCreateTable"`<br>
    `mvn exec:java -Dexec.mainClass="com.gigaspaces.HiveWriteToTable"`<br>

**5.** Start insightedge with 2 GSC:<br>
 `./gs.sh host run-agent --auto --gsc=2`

**6.** Deploy ax-pu jar:<br>
`./gs.sh pu deploy AxPu /home/vagrant/insightedge-training/Day5/20-data-lake-acceleration/Lab-19-Query-speed-batch/ax-pu/target/AxPu.jar`

**7.** Deploy index space pu (gs-home/insightedge/lib/analytics-xtreme/batch-index/batch-index.jar:<br>
`./gs.sh pu deploy index-pu ../insightedge/lib/analytics-xtreme/batch-index/batch-index.jar`

**8.** See existing types and data in index space using web-ui:
![Screenshot](./Pictures/Picture1.png)
 
**9.** Perform queries - AXClient in remote-client module
`mvn exec:java -Dexec.mainClass="com.gigaspaces.AXClient"`

**10.** Examine statistics and index distributions, see files created in:<br>
    `remote-client/logs`

**11.** Write new entries to speed space Client in remote-client module<br>
`mvn exec:java -Dexec.mainClass="com.gigaspaces.Client"`

**12.** Go to zeppelin, import exercise notebook<br>

**13.** Create new **insightedge_jdbc_ax** interpreter (jdbc group) set url to `jdbc:insightedge:url=jini://*/*/speedSpace;analyticsXtreme.enabled=true`<br>
add property `com.gigaspaces.jdbc.autoCommit` and set its value to `true`<br>
Change `default.driver` to `com.gigaspaces.jdbc.Driver`

**14.** Create new  jdbc interpreter called **hive_jdbc** (jdbc group) set its  url to `hive2://hive-server:10000/;ssl=false`<br>
Change `default.driver` to `com.gigaspaces.jdbc.Driver`

**15.** Change in interpreter **insightedge_jdbc** `default.url` to `jdbc:insightedge:spaceName=speedSpace`

**16.** Follow instructions in the notebook 
