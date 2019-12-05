# Flight Delay Example

Data for the demo
Link:  http://stat-computing.org/dataexpo/2009/the-data.html

Instruction:<br>
**1.** Download from the above link, `2007.csv` file. Save the file on your local machine.<br>
**2.** Review the csv file. Decide which fields to keep in space.<br>
**3.** In IDE, go to DelayRec class and declare the required fields.<br>
* Please note Space Routing field is set to flightNumber.<br>
* Space Id is flightNumber and time.<br>
   
**4.** Build the project jar (mvn package).<br>
**5.** Start the InsightEdge Manager<br>
   * `./gs.sh host run-agent --auto --gsc=2`
   
**6.** Open the web-ui in a browser - (`http://localhost:8099`)<br>
**7.** deploy project jar (2,0 - 2 partitions with no backups):<br>
`./gs.sh pu deploy FlightDelayDemo /home/vagrant/insightedge-training/Day5/17-Flight-Delay-Demo/Lab-17.1-Flight-Delay-Demo/target/flightdelaydemo-1.0.0-SNAPSHOT.jar --partitions=2`<br>
**8.** Open Zeppelin in a web browser (`http://localhost:9090`).<br>
        Import `Lab 17.1 Example - Flight Delay Prediction Demo.json` notebook.<br>
**9.** Follow instructions in the notebook. Notifications will appear in the console output (gs.sh).<br>
**10.** Go to the web-ui to see the data in the space after each step.



